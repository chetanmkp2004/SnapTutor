package com.snaptutor.app.data.repository

import com.snaptutor.app.data.models.StudentProgress
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for student progress tracking.
 */
interface ProgressRepository {
    fun getProgress(): Flow<StudentProgress>
    suspend fun updateProgress(progress: StudentProgress)
}
