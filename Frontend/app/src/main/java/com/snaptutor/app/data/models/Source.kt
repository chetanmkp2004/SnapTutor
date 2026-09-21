package com.snaptutor.app.data.models

import androidx.compose.runtime.Immutable

/**
 * A source reference for an explanation (textbook, curriculum document, etc.).
 */
@Immutable
data class Source(
    val title: String,
    val type: SourceType,
    val url: String = "",
    val excerpt: String = ""
)

enum class SourceType {
    TEXTBOOK,
    CURRICULUM,
    ONLINE,
    ON_DEVICE
}
