package com.snaptutor.app.data.models

import androidx.compose.runtime.Immutable

/**
 * Result of a completed quiz evaluation.
 */
@Immutable
data class EvaluationResult(
    val quizId: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val answers: List<AnswerRecord>,
    val timeTakenSeconds: Long = 0
) {
    // PLACEHOLDER FORMULA: mastery = questionsCorrect / questionsAttempted.
    // Real spaced-repetition / decay logic is deferred.
    val masteryScore: Double
        get() = if (totalQuestions > 0) correctAnswers.toDouble() / totalQuestions else 0.0

    val percentage: Int
        get() = (masteryScore * 100).toInt()
}

@Immutable
data class AnswerRecord(
    val questionId: String,
    val selectedIndex: Int,
    val isCorrect: Boolean
)
