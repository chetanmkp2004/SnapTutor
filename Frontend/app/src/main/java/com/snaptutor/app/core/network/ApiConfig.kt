package com.snaptutor.app.core.network

/**
 * API configuration constants.
 * NOT wired to real endpoints — placeholder values for future FastAPI backend integration.
 */
object ApiConfig {
    // PLACEHOLDER: Replace with actual backend URL when FastAPI server is deployed
    const val BASE_URL = "https://api.snaptutor.example.com/v1/"
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L
}
