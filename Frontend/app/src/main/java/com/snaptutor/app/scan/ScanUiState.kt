package com.snaptutor.app.scan

enum class InputMode {
    CAMERA,
    TEXT
}

data class ScanUiState(
    val inputMode: InputMode = InputMode.CAMERA,
    val inputText: String = "",
    val extractedText: String = "",
    val isProcessing: Boolean = false,
    val error: String? = null
)
