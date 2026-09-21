package com.snaptutor.app.data.repository

import com.snaptutor.app.data.models.LearningResponse

/**
 * Repository interface for learning/explanation operations.
 * Real implementations (Room-backed, Retrofit-backed) are added later
 * without changing ViewModels or UI.
 */
interface LearningRepository {
    suspend fun getExplanation(questionText: String): LearningResponse
}
