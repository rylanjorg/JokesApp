package com.rylan.jokesapp.models

data class JokeModel(
    val id: Int,
    var question: String,
    var answer: String,
    var answerIsVisible: Boolean
)