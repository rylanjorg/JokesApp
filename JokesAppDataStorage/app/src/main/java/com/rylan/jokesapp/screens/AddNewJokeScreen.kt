package com.rylan.jokesapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rylan.jokesapp.data.JokesViewModelInterface
import com.rylan.jokesapp.models.JokeModel
import com.rylan.jokesapp.ui.theme.Pink40

@OptIn(ExperimentalMaterial3Api::class)
public @Composable
fun AddJokeScreen(navController: NavHostController, viewModel: JokesViewModelInterface) {
    var jokeQuestion by remember { mutableStateOf("")}
    var jokeAnswer by remember { mutableStateOf("")}
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Pink40)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Add a Joke",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onTertiary)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = jokeQuestion,
                onValueChange = { textFieldContents -> jokeQuestion = textFieldContents},
                label = {Text("Joke Question")})
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = jokeAnswer,
                onValueChange = { textFieldContents -> jokeAnswer = textFieldContents},
                label = { Text("Answer")})
            Button(onClick = {
                navController.navigate("show-items")
                viewModel.addJoke(JokeModel(4, jokeQuestion, jokeAnswer, false))
            }){
                Text("Add")
            }
        }

    }

}