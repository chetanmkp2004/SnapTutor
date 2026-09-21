package com.snaptutor.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey val id: String = "current",
    val totalQuestionsAttempted: Int = 0,
    val totalCorrect: Int = 0,
    val topicsStudied: String = "", // JSON-serialized list
    val weakTopics: String = "",    // JSON-serialized list
    val streakDays: Int = 0,
    val lastStudiedTimestamp: Long = 0
)
