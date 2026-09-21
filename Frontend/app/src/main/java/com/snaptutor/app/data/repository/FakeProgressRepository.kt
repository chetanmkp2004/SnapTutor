package com.snaptutor.app.data.repository

import com.snaptutor.app.data.models.StudentProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * In-memory mock implementation of [ProgressRepository].
 * Returns sample progress data.
 */
class FakeProgressRepository : ProgressRepository {

    private val progressFlow = MutableStateFlow(
        StudentProgress(
            totalQuestionsAttempted = 24,
            totalCorrect = 18,
            topicsStudied = listOf("Linear Equations", "Quadratic Equations", "Algebra Basics"),
            weakTopics = listOf("Quadratic Equations", "Word Problems"),
            streakDays = 3,
            lastStudiedTimestamp = System.currentTimeMillis()
        )
    )

    override fun getProgress(): Flow<StudentProgress> = progressFlow

    override suspend fun updateProgress(progress: StudentProgress) {
        progressFlow.value = progress
    }
}
