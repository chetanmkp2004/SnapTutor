package com.snaptutor.app.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snaptutor.app.ai.ocr.OcrProcessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel managing camera capture and OCR text extraction.
 *
 * // DEFERRED: image retention policy — captured images are not stored beyond session
 * For this phase, images are transient and immediately discarded after simulated extraction.
 */
class ScanViewModel(
    private val ocrProcessor: OcrProcessor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState: StateFlow<ScanUiState> = _uiState.asStateFlow()

    fun setInputMode(mode: InputMode) {
        _uiState.update { it.copy(inputMode = mode) }
    }

    fun onInputTextChanged(text: String) {
        _uiState.update { it.copy(inputText = text, error = null) }
    }

    fun onExtractedTextChanged(text: String) {
        _uiState.update { it.copy(extractedText = text, error = null) }
    }

    fun captureAndExtract(imageUri: String = "placeholder://camera_capture.jpg") {
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true, error = null) }
            try {
                val recognized = ocrProcessor.extractText(imageUri)
                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        extractedText = recognized
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        error = e.message ?: "Failed to extract text from image"
                    )
                }
            }
        }
    }

    fun clear() {
        _uiState.value = ScanUiState()
    }
}
