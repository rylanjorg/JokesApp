package com.rylan.jokesapp.data

import com.rylan.jokesapp.models.JokeModel

class JokesRepository(val jokeDao: JokesDAO) {
    suspend fun getAll() = jokeDao.getAll()

    suspend fun findByKeyword(phrase: String) = jokeDao.getWithSearch(phrase)

    suspend fun getById(id: Int) = jokeDao.getById(id)

    suspend fun addOne(jokeModel: JokeModel) = jokeDao.addOne(jokeModel)

    suspend fun deleteOne(jokeModel: JokeModel) = jokeDao.deleteById(jokeModel.id)
}