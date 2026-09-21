package com.snaptutor.app.adapt

import com.snaptutor.app.data.models.AnswerRecord
import com.snaptutor.app.data.models.Difficulty
import com.snaptutor.app.data.models.EvaluationResult
import org.junit.Assert.assertEquals
import org.junit.Test

class AdaptationManagerTest {

    @Test
    fun `high mastery score triggers increase difficulty recommendation`() {
        val result = EvaluationResult(
            quizId = "test-q1",
            totalQuestions = 10,
            correctAnswers = 8,
            incorrectAnswers = 2,
            answers = emptyList()
        )

        val adaptation = AdaptationManager.adapt(result, Difficulty.EASY)

        assertEquals(AdaptationRecommendation.INCREASE_DIFFICULTY, adaptation.recommendation)
        assertEquals(Difficulty.MEDIUM, adaptation.nextDifficulty)
    }

    @Test
    fun `moderate mastery score maintains current difficulty`() {
        val result = EvaluationResult(
            quizId = "test-q2",
            totalQuestions = 10,
            correctAnswers = 5,
            incorrectAnswers = 5,
            answers = emptyList()
        )

        val adaptation = AdaptationManager.adapt(result, Difficulty.MEDIUM)

        assertEquals(AdaptationRecommendation.MAINTAIN_DIFFICULTY, adaptation.recommendation)
        assertEquals(Difficulty.MEDIUM, adaptation.nextDifficulty)
    }

    @Test
    fun `low mastery score reinforces concept and lowers difficulty`() {
        val result = EvaluationResult(
            quizId = "test-q3",
            totalQuestions = 10,
            correctAnswers = 3,
            incorrectAnswers = 7,
            answers = emptyList()
        )

        val adaptation = AdaptationManager.adapt(result, Difficulty.HARD)

        assertEquals(AdaptationRecommendation.REINFORCE_CONCEPT, adaptation.recommendation)
        assertEquals(Difficulty.MEDIUM, adaptation.nextDifficulty)
    }

    @Test
    fun `very low mastery score recommends review basics`() {
        val result = EvaluationResult(
            quizId = "test-q4",
            totalQuestions = 10,
            correctAnswers = 1,
            incorrectAnswers = 9,
            answers = emptyList()
        )

        val adaptation = AdaptationManager.adapt(result, Difficulty.MEDIUM)

        assertEquals(AdaptationRecommendation.REVIEW_BASICS, adaptation.recommendation)
        assertEquals(Difficulty.EASY, adaptation.nextDifficulty)
    }
}
