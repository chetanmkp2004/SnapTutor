package com.snaptutor.app.data.models

import androidx.compose.runtime.Immutable

/**
 * A single question within a quiz.
 */
@Immutable
data class QuizQuestion(
    val id: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String = ""
)
