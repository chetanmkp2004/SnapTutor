package com.snaptutor.app.adapt

import com.snaptutor.app.core.utils.Constants
import com.snaptutor.app.data.models.Difficulty
import com.snaptutor.app.data.models.EvaluationResult

/**
 * Pure function: EvaluationResult → AdaptationState.
 * Determines the next learning path based on quiz performance.
 *
 * PLACEHOLDER THRESHOLD: >70% mastery → increase difficulty, <40% → reinforce concept.
 * This is a provisional heuristic, not a real adaptive learning algorithm.
 * Real implementation should use spaced-repetition, learning curve modeling, etc.
 */
object AdaptationManager {

    fun adapt(result: EvaluationResult, currentDifficulty: Difficulty): AdaptationState {
        val mastery = result.masteryScore

        return when {
            mastery >= Constants.MASTERY_INCREASE_THRESHOLD -> AdaptationState(
                recommendation = AdaptationRecommendation.INCREASE_DIFFICULTY,
                nextDifficulty = increaseDifficulty(currentDifficulty),
                message = "Great work! You scored ${result.percentage}%. " +
                        "Ready for more challenging problems.",
                suggestedTopics = emptyList()
            )

            mastery >= Constants.MASTERY_REINFORCE_THRESHOLD -> AdaptationState(
                recommendation = AdaptationRecommendation.MAINTAIN_DIFFICULTY,
                nextDifficulty = currentDifficulty,
                message = "Good effort! You scored ${result.percentage}%. " +
                        "Let's practice a few more at this level.",
                suggestedTopics = emptyList()
            )

            mastery >= 0.2 -> AdaptationState(
                recommendation = AdaptationRecommendation.REINFORCE_CONCEPT,
                nextDifficulty = decreaseDifficulty(currentDifficulty),
                message = "You scored ${result.percentage}%. " +
                        "Let's review the core concepts before moving on.",
                suggestedTopics = listOf("Review explanation", "Try simpler problems")
            )

            else -> AdaptationState(
                recommendation = AdaptationRecommendation.REVIEW_BASICS,
                nextDifficulty = Difficulty.EASY,
                message = "You scored ${result.percentage}%. " +
                        "Let's go back to the basics and build a stronger foundation.",
                suggestedTopics = listOf("Review fundamentals", "Watch guided walkthrough")
            )
        }
    }

    private fun increaseDifficulty(current: Difficulty): Difficulty = when (current) {
        Difficulty.EASY -> Difficulty.MEDIUM
        Difficulty.MEDIUM -> Difficulty.HARD
        Difficulty.HARD -> Difficulty.HARD
    }

    private fun decreaseDifficulty(current: Difficulty): Difficulty = when (current) {
        Difficulty.EASY -> Difficulty.EASY
        Difficulty.MEDIUM -> Difficulty.EASY
        Difficulty.HARD -> Difficulty.MEDIUM
    }
}
