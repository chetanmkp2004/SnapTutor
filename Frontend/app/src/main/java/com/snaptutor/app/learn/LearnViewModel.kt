package com.snaptutor.app.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snaptutor.app.core.state.UiState
import com.snaptutor.app.data.repository.LearningRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LearnViewModel(
    private val learningRepository: LearningRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<LearnData>>(UiState.Loading)
    val uiState: StateFlow<UiState<LearnData>> = _uiState.asStateFlow()

    private var currentQuestion: String = ""

    fun loadExplanation(questionText: String) {
        currentQuestion = questionText
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = learningRepository.getExplanation(questionText)
                _uiState.value = UiState.Success(
                    LearnData(
                        questionText = questionText,
                        response = response
                    )
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Failed to generate explanation")
            }
        }
    }

    fun retry() {
        if (currentQuestion.isNotBlank()) {
            loadExplanation(currentQuestion)
        }
    }
}
