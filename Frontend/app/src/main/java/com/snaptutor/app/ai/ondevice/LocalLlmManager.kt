package com.snaptutor.app.ai.ondevice

import com.snaptutor.app.core.utils.Constants
import kotlinx.coroutines.delay

/**
 * Interface for on-device LLM inference.
 * Real implementation will load a local model (e.g., Gemma) — not loaded in this phase.
 * Do not load any real model or ML dependency yet.
 */
interface LocalLlmManager {
    suspend fun generateExplanation(question: String): LocalLlmResult
    fun isModelLoaded(): Boolean
}

/**
 * Fake implementation returning the sample explanation from the context doc
 * ("Solve 2x + 5 = 15" → step-by-step to x = 5).
 * Does NOT claim to be real AI in any output — this is mock data only.
 */
class FakeLocalLlmManager : LocalLlmManager {

    override suspend fun generateExplanation(question: String): LocalLlmResult {
        delay(Constants.FAKE_LLM_DELAY_MS) // Simulate model inference time

        return LocalLlmResult(
            explanation = "To solve the equation 2x + 5 = 15, we need to isolate the variable x " +
                    "by performing inverse operations on both sides of the equation.",
            steps = listOf(
                "Start with the equation: 2x + 5 = 15",
                "Subtract 5 from both sides: 2x + 5 - 5 = 15 - 5",
                "Simplify: 2x = 10",
                "Divide both sides by 2: 2x / 2 = 10 / 2",
                "Solution: x = 5"
            ),
            answer = "x = 5",
            confidence = 0.95f
        )
    }

    override fun isModelLoaded(): Boolean = true // Fake always reports loaded
}
