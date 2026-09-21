package com.snaptutor.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuizRequest(
    val topic: String,
    val difficulty: String = "MEDIUM",
    val numberOfQuestions: Int = 3
)
