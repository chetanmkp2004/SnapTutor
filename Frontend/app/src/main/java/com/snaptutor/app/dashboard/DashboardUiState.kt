package com.snaptutor.app.dashboard

import androidx.compose.runtime.Immutable
import com.snaptutor.app.data.models.StudentProgress
import com.snaptutor.app.data.models.SyncState

@Immutable
data class DashboardData(
    val progress: StudentProgress,
    val recentTopic: String,
    val recentQuestion: String,
    val syncState: SyncState
)
