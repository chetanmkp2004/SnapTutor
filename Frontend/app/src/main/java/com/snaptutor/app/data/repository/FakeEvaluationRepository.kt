package com.snaptutor.app.data.repository

import com.snaptutor.app.core.utils.Constants
import com.snaptutor.app.data.models.Difficulty
import com.snaptutor.app.data.models.Quiz
import com.snaptutor.app.data.models.QuizQuestion
import kotlinx.coroutines.delay

/**
 * In-memory mock implementation of [EvaluationRepository].
 * Uses the 3x + 4 = 19 example from the context doc.
 */
class FakeEvaluationRepository : EvaluationRepository {

    private val sampleQuiz = Quiz(
        id = "quiz-001",
        title = "Linear Equations Practice",
        topic = "Linear Equations",
        difficulty = Difficulty.MEDIUM,
        questions = listOf(
            QuizQuestion(
                id = "qq-001",
                questionText = "Solve: 3x + 4 = 19",
                options = listOf("x = 3", "x = 5", "x = 7", "x = 4"),
                correctAnswerIndex = 1,
                explanation = "Subtract 4 from both sides: 3x = 15. Divide by 3: x = 5."
            ),
            QuizQuestion(
                id = "qq-002",
                questionText = "What is the first step to solve 5y - 3 = 12?",
                options = listOf(
                    "Divide both sides by 5",
                    "Subtract 12 from both sides",
                    "Add 3 to both sides",
                    "Multiply both sides by 3"
                ),
                correctAnswerIndex = 2,
                explanation = "Add 3 to both sides to isolate the term with the variable: 5y = 15."
            ),
            QuizQuestion(
                id = "qq-003",
                questionText = "If 2(x + 3) = 14, what is x?",
                options = listOf("x = 4", "x = 5", "x = 7", "x = 3"),
                correctAnswerIndex = 0,
                explanation = "Distribute: 2x + 6 = 14. Subtract 6: 2x = 8. Divide by 2: x = 4."
            )
        )
    )

    override suspend fun getQuizForTopic(topic: String): Quiz {
        delay(Constants.FAKE_NETWORK_DELAY_MS)
        return sampleQuiz
    }

    override fun getSampleQuiz(): Quiz = sampleQuiz
}
