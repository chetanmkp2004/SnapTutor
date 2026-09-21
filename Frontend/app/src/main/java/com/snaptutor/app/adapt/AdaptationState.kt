package com.snaptutor.app.adapt

import com.snaptutor.app.data.models.Difficulty

/**
 * Represents the adaptive learning recommendation after evaluation.
 */
data class AdaptationState(
    val recommendation: AdaptationRecommendation,
    val nextDifficulty: Difficulty,
    val message: String,
    val suggestedTopics: List<String> = emptyList()
)

enum class AdaptationRecommendation {
    INCREASE_DIFFICULTY,
    MAINTAIN_DIFFICULTY,
    REINFORCE_CONCEPT,
    REVIEW_BASICS
}
