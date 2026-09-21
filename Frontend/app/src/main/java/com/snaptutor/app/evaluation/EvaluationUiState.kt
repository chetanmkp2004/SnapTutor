package com.snaptutor.app.evaluation

import com.snaptutor.app.adapt.AdaptationState
import com.snaptutor.app.data.models.EvaluationResult
import com.snaptutor.app.data.models.Quiz

data class EvaluationQuizState(
    val quiz: Quiz,
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(), // questionIndex -> selectedOptionIndex
    val isSubmitted: Boolean = false,
    val result: EvaluationResult? = null,
    val adaptationState: AdaptationState? = null
)
