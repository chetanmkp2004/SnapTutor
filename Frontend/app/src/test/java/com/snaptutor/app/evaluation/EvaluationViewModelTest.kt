package com.snaptutor.app.evaluation

import com.snaptutor.app.core.state.UiState
import com.snaptutor.app.data.models.Difficulty
import com.snaptutor.app.data.models.Quiz
import com.snaptutor.app.data.models.QuizQuestion
import com.snaptutor.app.data.models.StudentProgress
import com.snaptutor.app.data.repository.EvaluationRepository
import com.snaptutor.app.data.repository.ProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EvaluationViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val testQuiz = Quiz(
        id = "test-quiz-1",
        title = "Linear Equations Test",
        topic = "Linear Equations",
        difficulty = Difficulty.MEDIUM,
        questions = listOf(
            QuizQuestion(
                id = "q1",
                questionText = "Solve 2x = 10",
                options = listOf("x = 2", "x = 5", "x = 10"),
                correctAnswerIndex = 1,
                explanation = "Divide by 2 to get x = 5"
            ),
            QuizQuestion(
                id = "q2",
                questionText = "Solve x + 3 = 7",
                options = listOf("x = 4", "x = 3", "x = 10"),
                correctAnswerIndex = 0,
                explanation = "Subtract 3 to get x = 4"
            )
        )
    )

    private val fakeEvaluationRepository = object : EvaluationRepository {
        override suspend fun getQuizForTopic(topic: String): Quiz = testQuiz
        override fun getSampleQuiz(): Quiz = testQuiz
    }

    private val fakeProgressRepository = object : ProgressRepository {
        private val state = MutableStateFlow(StudentProgress())
        override fun getProgress(): Flow<StudentProgress> = state
        override suspend fun updateProgress(progress: StudentProgress) {
            state.value = progress
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadQuiz loads questions successfully into state`() = runTest(testDispatcher) {
        val viewModel = EvaluationViewModel(fakeEvaluationRepository, fakeProgressRepository)
        viewModel.loadQuiz("Linear Equations")

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        val data = (state as UiState.Success).data
        assertEquals("test-quiz-1", data.quiz.id)
        assertEquals(2, data.quiz.questions.size)
        assertEquals(false, data.isSubmitted)
    }

    @Test
    fun `submitQuiz calculates score and updates result`() = runTest(testDispatcher) {
        val viewModel = EvaluationViewModel(fakeEvaluationRepository, fakeProgressRepository)
        viewModel.loadQuiz("Linear Equations")
        advanceUntilIdle()

        // Question 0: choose option 1 (correct, index 1)
        viewModel.selectOption(questionIndex = 0, optionIndex = 1)
        // Question 1: choose option 2 (incorrect, correct is index 0)
        viewModel.selectOption(questionIndex = 1, optionIndex = 2)

        viewModel.submitQuiz()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        val data = (state as UiState.Success).data
        assertTrue(data.isSubmitted)

        val result = data.result
        assertNotNull(result)
        assertEquals(2, result!!.totalQuestions)
        assertEquals(1, result.correctAnswers)
        assertEquals(1, result.incorrectAnswers)
        assertEquals(50, result.percentage)
        assertNotNull(data.adaptationState)
    }
}
