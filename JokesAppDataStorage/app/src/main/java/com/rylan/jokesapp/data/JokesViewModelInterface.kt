package com.rylan.jokesapp.data

import com.rylan.jokesapp.models.JokeModel

interface JokesViewModelInterface {
    var jokesList : MutableList<JokeModel>
    var jokeSearchResult : MutableList<JokeModel>

    fun getAllJokes()

    fun addJoke(joke: JokeModel)

    fun removeJoke(joke: JokeModel)

    fun findJokesByKeyword(findPhrase: String)

    fun hikeShowJoke(joke: JokeModel)
}