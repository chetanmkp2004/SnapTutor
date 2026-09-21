package com.snaptutor.app.ai.ondevice

/**
 * Result from the local LLM inference.
 */
data class LocalLlmResult(
    val explanation: String,
    val steps: List<String>,
    val answer: String,
    val confidence: Float = 0f
)
