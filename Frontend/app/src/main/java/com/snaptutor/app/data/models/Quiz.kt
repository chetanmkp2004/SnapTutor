package com.snaptutor.app.data.models

import androidx.compose.runtime.Immutable

/**
 * A complete quiz associated with a topic or question.
 */
@Immutable
data class Quiz(
    val id: String,
    val title: String,
    val topic: String,
    val questions: List<QuizQuestion>,
    val difficulty: Difficulty = Difficulty.MEDIUM
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}
