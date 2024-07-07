package com.rylan.jokesapp.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rylan.jokesapp.Joke1
import com.rylan.jokesapp.data.JokesViewModelInterface

public @Composable
fun ShowJokesScreen(viewModel: JokesViewModelInterface) {
    var jokes = viewModel.jokesList


    if (viewModel.jokeSearchResult.size == 0){
        jokes = viewModel.jokesList
    } else {
        jokes = viewModel.jokeSearchResult
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn() {
            items(jokes.size) { index ->
                Joke1(jokeModel = jokes[index],
                    {
                        Log.d("ShowJokesScreen", "Joke clicked: ${jokes[index]}")
                        Log.d("ShowJokesScreen", "Index number clicked: $index")
                        viewModel.hikeShowJoke(jokes[index])

                        /*var changedJoke = jokes[it].copy(answerIsVisible = !jokes[it].answerIsVisible)
                        jokes[it] = changedJoke*/


                    })
            }
        }
    }
}