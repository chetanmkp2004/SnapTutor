package com.snaptutor.app.data.repository

import com.snaptutor.app.data.models.Quiz
import com.snaptutor.app.data.models.QuizQuestion

// ASSUMPTION: Quiz generation is assumed to work OFFLINE using locally cached question banks
// for this phase. In a real implementation, quiz generation may optionally use the network
// (FastAPI + LLM) for dynamic question creation when online, but must fall back to a local
// pool when offline. The connectivity requirement is an unresolved product decision.

/**
 * Repository interface for quiz/evaluation operations.
 */
interface EvaluationRepository {
    suspend fun getQuizForTopic(topic: String): Quiz
    fun getSampleQuiz(): Quiz
}
