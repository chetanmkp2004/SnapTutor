package com.snaptutor.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExplainResponse(
    val explanation: String,
    val steps: List<String>,
    val keyConcepts: List<String>,
    val sources: List<SourceDto> = emptyList(),
    val answer: String = ""
)

@Serializable
data class SourceDto(
    val title: String,
    val type: String,
    val url: String = "",
    val excerpt: String = ""
)
