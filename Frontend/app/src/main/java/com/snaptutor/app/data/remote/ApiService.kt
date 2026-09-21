package com.snaptutor.app.data.remote

import com.snaptutor.app.data.remote.dto.ExplainRequest
import com.snaptutor.app.data.remote.dto.ExplainResponse
import com.snaptutor.app.data.remote.dto.QuizRequest
import com.snaptutor.app.data.remote.dto.QuizResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit API service definition for the FastAPI backend.
 * NOT wired to real endpoints — this is a compile-time contract only.
 * Real backend integration happens in a later phase.
 */
interface ApiService {

    @POST("explain")
    suspend fun getExplanation(@Body request: ExplainRequest): ExplainResponse

    @POST("quiz/generate")
    suspend fun generateQuiz(@Body request: QuizRequest): QuizResponse
}
