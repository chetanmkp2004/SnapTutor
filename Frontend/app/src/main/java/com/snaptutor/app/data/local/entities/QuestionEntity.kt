package com.snaptutor.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val text: String,
    val subject: String = "",
    val topic: String = "",
    val source: String = "TYPED", // QuestionSource name
    val timestamp: Long = System.currentTimeMillis()
    // DEFERRED: image retention policy is a deferred product decision.
    // No imageUri column in this phase — images are not persisted or transmitted.
)
