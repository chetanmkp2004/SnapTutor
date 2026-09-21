package com.snaptutor.app.data.models

import androidx.compose.runtime.Immutable

/**
 * Tracks a student's learning progress across topics.
 */
@Immutable
data class StudentProgress(
    val totalQuestionsAttempted: Int = 0,
    val totalCorrect: Int = 0,
    val topicsStudied: List<String> = emptyList(),
    val weakTopics: List<String> = emptyList(),
    val streakDays: Int = 0,
    val lastStudiedTimestamp: Long = 0
) {
    // PLACEHOLDER FORMULA: mastery = questionsCorrect / questionsAttempted.
    // This is a simple ratio for the hackathon prototype.
    // Real implementation should use spaced-repetition algorithms (e.g., SM-2)
    // with time-decay factors and per-topic tracking.
    val overallMastery: Double
        get() = if (totalQuestionsAttempted > 0) {
            totalCorrect.toDouble() / totalQuestionsAttempted
        } else {
            0.0
        }

    val masteryPercentage: Int
        get() = (overallMastery * 100).toInt()
}
