package com.rylan.jokesapp.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.rylan.jokesapp.data.JokesViewModelInterface

@OptIn(ExperimentalMaterial3Api::class)
public @Composable
fun SearchScreen(navController: NavHostController, viewModel: JokesViewModelInterface) {
    var searchString by remember { mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Search for a Joke",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary)
        TextField(value = searchString,
            onValueChange = {textFieldContents -> searchString = textFieldContents},
            label = { Text("Search for...")}
        )
        Button(onClick = {
            viewModel.findJokesByKeyword(searchString)
            navController.navigate("show-items")
        }){
            Text("Search")
        }
    }
}