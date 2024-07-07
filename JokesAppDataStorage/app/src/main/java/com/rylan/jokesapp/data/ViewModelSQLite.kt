package com.rylan.jokesapp.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.rylan.jokesapp.models.JokeModel

class ViewModelSQLite (context: Context) : JokesViewModelInterface, ViewModel() {
    override var jokesList: MutableList<JokeModel> = mutableStateListOf<JokeModel>()
    override var jokeSearchResult: MutableList<JokeModel> = mutableStateListOf<JokeModel>()


    val jokesDAO = JokesDAO(context, null)

    val repository = JokesRepository(jokesDAO)

    init {
        if (jokesList.size == 0){
            jokesList.addAll(jokesDAO.getAll())
        }

        /*if (jokesList.size == 0)
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
            ))*/
    }


    override fun getAllJokes() {
        jokesList.clear()
        jokesList.addAll(jokesDAO.getAll())
    }

    override fun addJoke(joke: JokeModel) {
        val result = jokesDAO.addOne(joke)

        if (result != null){
            jokesList.add(result)
        }
    }

    override fun removeJoke(joke: JokeModel) {
        val result = jokesDAO.deleteById(joke.id)

        if (result != null){
            jokesList.remove(joke)
        }
    }

    override fun findJokesByKeyword(findPhrase: String) {
        jokeSearchResult.clear()
        jokeSearchResult.addAll(jokesDAO.getWithSearch(findPhrase))
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