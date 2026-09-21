package com.snaptutor.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey val id: String,
    val title: String,
    val topic: String,
    val questionsJson: String, // JSON-serialized list of QuizQuestion
    val difficulty: String = "MEDIUM",
    val timestamp: Long = System.currentTimeMillis()
)
