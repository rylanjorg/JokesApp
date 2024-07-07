package com.rylan.jokesapp.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.rylan.jokesapp.models.JokeModel

class ViewModelInMemory : JokesViewModelInterface, ViewModel() {
    override var jokesList: MutableList<JokeModel> = mutableStateListOf<JokeModel>()
    override var jokeSearchResult: MutableList<JokeModel> = mutableStateListOf<JokeModel>()

    init {
        if (jokesList.size == 0)
            jokesList.addAll(mutableStateListOf(
                JokeModel(
                    0,
                    "What time is it when an elephant sits on your fence?",
                    "Time to get a new fence",
                    true
                ),
                JokeModel(
                    1,
                    "What is red and smells like blue paint?",
                    "Red paint",
                    false
                ), JokeModel(
                    2,
                    "Why did the chicken cross the playground?",
                    "To get to the other slide",
                    true
                ), JokeModel(
                    3,
                    "What did the mother buffalo say to her child when leaving him at school?",
                    "Bison",
                    false
                )
            ))
    }


    override fun getAllJokes() {

    }

    override fun addJoke(joke: JokeModel) {
        jokesList.add(joke)
    }

    override fun removeJoke(joke: JokeModel) {
        jokesList.remove(joke)
    }

    override fun findJokesByKeyword(findPhrase: String) {
        jokeSearchResult.clear()

        for (joke in jokesList){
            if (joke.question.contains(findPhrase, ignoreCase = true) ||
                joke.answer.contains(findPhrase, ignoreCase = true)){
                jokeSearchResult.add(joke)
            }
        }
    }

    override fun hikeShowJoke(joke: JokeModel) {
        if (jokeSearchResult.size == 0){
            jokesList.forEach(
                { it.answerIsVisible = false}
            )
            var position = jokesList.indexOf(joke)

            if (position >= 0){
                jokesList[position] = jokesList[position].copy(answerIsVisible = true)
            }
        } else{
            jokeSearchResult.forEach(
                { it.answerIsVisible = false}
            )

            var position = jokeSearchResult.indexOf(joke)

            if (position >= 0){
                jokeSearchResult[position] = jokeSearchResult[position].copy(answerIsVisible = true)
            }
        }


    }

}