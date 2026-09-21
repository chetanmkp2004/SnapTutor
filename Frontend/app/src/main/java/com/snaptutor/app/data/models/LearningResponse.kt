package com.snaptutor.app.data.models

import androidx.compose.runtime.Immutable

/**
 * The complete learning response for a question, containing explanation,
 * step-by-step breakdown, key concepts, and source references.
 */
@Immutable
data class LearningResponse(
    val questionId: String,
    val explanation: String,
    val steps: List<String>,
    val keyConcepts: List<String>,
    val sources: List<Source>,
    val isOnDevice: Boolean,
    val answer: String = ""
)
