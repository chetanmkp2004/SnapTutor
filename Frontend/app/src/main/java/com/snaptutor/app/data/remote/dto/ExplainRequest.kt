package com.snaptutor.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExplainRequest(
    val questionText: String,
    val subject: String = "",
    val topic: String = "",
    val preferredLanguage: String = "en"
)
