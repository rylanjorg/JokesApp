package com.rylan.jokesapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rylan.jokesapp.data.JokesViewModelInterface
import com.rylan.jokesapp.data.ViewModelInMemory
import com.rylan.jokesapp.data.ViewModelSQLite
import com.rylan.jokesapp.models.JokeModel
import com.rylan.jokesapp.models.ScreenInfo
import com.rylan.jokesapp.screens.AddJokeScreen
import com.rylan.jokesapp.screens.LoginScreen
import com.rylan.jokesapp.screens.SearchScreen
import com.rylan.jokesapp.screens.ShowJokesScreen
import com.rylan.jokesapp.ui.theme.JokesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokesAppTheme{
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp() {
    val context = LocalContext.current

    val viewModel: JokesViewModelInterface = remember { ViewModelSQLite(context) }

    val navController = rememberNavController()

    val screens = listOf(
        ScreenInfo(
            routeName = "login",
            title = "Login",
            navIcon = Icons.Default.AccountBox,
            composable =  { LoginScreen( navController) }
        ),  ScreenInfo(
            routeName = "add-item",
            title = "Add Item",
            navIcon = Icons.Default.Add,
            composable =  { AddJokeScreen(navController, viewModel) }
        ), ScreenInfo(
            routeName = "show-items",
            title = "Jokes",
            navIcon = Icons.Default.List,
            composable =  { ShowJokesScreen(viewModel) }
        )
        , ScreenInfo(
            routeName = "search",
            title = "Search",
            navIcon = Icons.Default.Search,
            composable =  { SearchScreen(navController, viewModel) }
        )
    )

    Scaffold(
        bottomBar = { BottomBar(navController = navController, screens = screens) },
        modifier = Modifier.padding(10.dp)
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)){
            NavigationGraph(navController = navController, screens = screens)
        }
    }

}

private @Composable
fun BottomBar(navController: NavHostController, screens: List<ScreenInfo>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(){
        screens.forEach{ screen->
            NavigationBarItem(
                label = {
                    Text(text = screen.title)
                },
                icon = {
                    Icon(
                        imageVector = screen.navIcon,
                        contentDescription = "Navigation Icon"
                    )
                },
                selected = currentDestination?.hierarchy?.any{
                    it.route == screen.routeName
                } == true,

                onClick = {
                    navController.navigate(screen.routeName){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

private @Composable
fun NavigationGraph(navController: NavHostController, screens: List<ScreenInfo>) {
    NavHost(
        navController = navController,
        startDestination = screens[0].routeName
    ){
        screens.forEach(){screen ->
            composable(route = screen.routeName) {screen.composable()}
        }
    }
}


@Composable
fun Joke1(jokeModel: JokeModel, changeVisibility: (id: Int) -> Unit){
    Column{
        Text(modifier = Modifier.padding(10.dp),
            text = "Joke number: ${jokeModel.id}")
        Text(modifier = Modifier
            .padding(10.dp)
            .clickable {
                changeVisibility(jokeModel.id)
                Log.d("Joke tag", "Joke 1: $jokeModel")
            },
            color = Color.Blue,
            fontSize = 8.em,
            lineHeight = 1.em,
            textAlign = TextAlign.Center,
            text = jokeModel.question)
        if (jokeModel.answerIsVisible)
            Text(
                modifier = Modifier.padding(10.dp),
                text = jokeModel.answer
            )
        Divider()
    }
}
