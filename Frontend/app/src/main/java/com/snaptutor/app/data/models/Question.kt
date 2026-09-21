package com.snaptutor.app.data.models

import androidx.compose.runtime.Immutable

/**
 * Domain model for a scanned or typed question.
 */
@Immutable
data class Question(
    val id: String,
    val text: String,
    val subject: String = "",
    val topic: String = "",
    val imageUri: String? = null, // DEFERRED: image retention policy is a deferred product decision. Do not persist or transmit captured images in this phase.
    val timestamp: Long = System.currentTimeMillis(),
    val source: QuestionSource = QuestionSource.TYPED
)

enum class QuestionSource {
    CAMERA,
    TYPED
}
