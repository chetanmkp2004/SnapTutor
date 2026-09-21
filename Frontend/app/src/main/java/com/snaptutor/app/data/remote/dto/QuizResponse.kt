package com.snaptutor.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuizResponse(
    val quizId: String,
    val title: String,
    val questions: List<QuizQuestionDto>
)

@Serializable
data class QuizQuestionDto(
    val id: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String = ""
)
