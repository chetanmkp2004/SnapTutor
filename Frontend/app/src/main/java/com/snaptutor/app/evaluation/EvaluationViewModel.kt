package com.snaptutor.app.evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snaptutor.app.adapt.AdaptationManager
import com.snaptutor.app.core.state.UiState
import com.snaptutor.app.data.models.AnswerRecord
import com.snaptutor.app.data.models.EvaluationResult
import com.snaptutor.app.data.models.StudentProgress
import com.snaptutor.app.data.repository.EvaluationRepository
import com.snaptutor.app.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class EvaluationViewModel(
    private val evaluationRepository: EvaluationRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<EvaluationQuizState>>(UiState.Loading)
    val uiState: StateFlow<UiState<EvaluationQuizState>> = _uiState.asStateFlow()

    private var currentTopic: String = ""

    fun loadQuiz(topic: String) {
        currentTopic = topic
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val quiz = evaluationRepository.getQuizForTopic(topic)
                _uiState.value = UiState.Success(
                    EvaluationQuizState(
                        quiz = quiz,
                        currentQuestionIndex = 0,
                        selectedAnswers = emptyMap(),
                        isSubmitted = false,
                        result = null,
                        adaptationState = null
                    )
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Failed to load practice quiz")
            }
        }
    }

    fun selectOption(questionIndex: Int, optionIndex: Int) {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (current.isSubmitted) return

        val updatedAnswers = current.selectedAnswers.toMutableMap().apply {
            put(questionIndex, optionIndex)
        }
        _uiState.value = UiState.Success(current.copy(selectedAnswers = updatedAnswers))
    }

    fun nextQuestion() {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (current.currentQuestionIndex < current.quiz.questions.size - 1) {
            _uiState.value = UiState.Success(
                current.copy(currentQuestionIndex = current.currentQuestionIndex + 1)
            )
        }
    }

    fun previousQuestion() {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (current.currentQuestionIndex > 0) {
            _uiState.value = UiState.Success(
                current.copy(currentQuestionIndex = current.currentQuestionIndex - 1)
            )
        }
    }

    fun submitQuiz() {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        val quiz = current.quiz
        val questions = quiz.questions

        var correct = 0
        val answerRecords = mutableListOf<AnswerRecord>()

        questions.forEachIndexed { index, q ->
            val selected = current.selectedAnswers[index] ?: -1
            val isCorrect = selected == q.correctAnswerIndex
            if (isCorrect) correct++
            answerRecords.add(
                AnswerRecord(
                    questionId = q.id,
                    selectedIndex = selected,
                    isCorrect = isCorrect
                )
            )
        }

        val result = EvaluationResult(
            quizId = quiz.id,
            totalQuestions = questions.size,
            correctAnswers = correct,
            incorrectAnswers = questions.size - correct,
            answers = answerRecords
        )

        val adaptation = AdaptationManager.adapt(result, quiz.difficulty)

        _uiState.value = UiState.Success(
            current.copy(
                isSubmitted = true,
                result = result,
                adaptationState = adaptation
            )
        )

        // Update progress repository
        viewModelScope.launch {
            val existing = progressRepository.getProgress().firstOrNull() ?: StudentProgress()
            val updated = existing.copy(
                totalQuestionsAttempted = existing.totalQuestionsAttempted + result.totalQuestions,
                totalCorrect = existing.totalCorrect + result.correctAnswers,
                lastStudiedTimestamp = System.currentTimeMillis()
            )
            progressRepository.updateProgress(updated)
        }
    }

    fun retakeQuiz() {
        if (currentTopic.isNotBlank()) {
            loadQuiz(currentTopic)
        }
    }
}
