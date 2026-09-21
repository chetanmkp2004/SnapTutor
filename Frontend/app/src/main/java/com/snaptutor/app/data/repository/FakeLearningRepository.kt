package com.snaptutor.app.data.repository

import com.snaptutor.app.core.utils.Constants
import com.snaptutor.app.data.models.LearningResponse
import com.snaptutor.app.data.models.Source
import com.snaptutor.app.data.models.SourceType
import kotlinx.coroutines.delay

/**
 * In-memory mock implementation of [LearningRepository].
 * Returns sample data from the SnapTutor context doc ("Solve 2x + 5 = 15").
 * Simulates a delay to make Loading → Success flow visibly testable.
 */
class FakeLearningRepository : LearningRepository {

    override suspend fun getExplanation(questionText: String): LearningResponse {
        delay(Constants.FAKE_LLM_DELAY_MS) // Simulate processing time

        return LearningResponse(
            questionId = "fake-q-001",
            explanation = "To solve the equation 2x + 5 = 15, we need to isolate the variable x " +
                    "by performing inverse operations on both sides of the equation.",
            steps = listOf(
                "Start with the equation: 2x + 5 = 15",
                "Subtract 5 from both sides: 2x + 5 - 5 = 15 - 5",
                "Simplify: 2x = 10",
                "Divide both sides by 2: 2x / 2 = 10 / 2",
                "Solution: x = 5"
            ),
            keyConcepts = listOf(
                "Linear Equations",
                "Inverse Operations",
                "Variable Isolation",
                "Equation Balancing"
            ),
            sources = listOf(
                Source(
                    title = "On-device explanation",
                    type = SourceType.ON_DEVICE,
                    excerpt = "Generated locally without network access."
                )
            ),
            isOnDevice = true,
            answer = "x = 5"
        )
    }
}
