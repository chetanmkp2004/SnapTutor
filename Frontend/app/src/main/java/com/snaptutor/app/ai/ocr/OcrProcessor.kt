package com.snaptutor.app.ai.ocr

import com.snaptutor.app.core.utils.Constants
import kotlinx.coroutines.delay

/**
 * Interface for OCR text extraction from images.
 * Real implementation will use ML Kit or similar — not loaded in this phase.
 */
interface OcrProcessor {
    suspend fun extractText(imageUri: String): String
}

/**
 * Fake OCR processor returning canned extracted text after a simulated delay,
 * so the Loading → Success flow is visibly testable.
 */
class FakeOcrProcessor : OcrProcessor {
    override suspend fun extractText(imageUri: String): String {
        delay(Constants.FAKE_OCR_DELAY_MS) // Simulate OCR processing time
        return "Solve 2x + 5 = 15"
    }
}
