package com.snaptutor.app.core.utils

object Constants {
    const val APP_NAME = "SnapTutor"
    const val APP_TAGLINE = "Learn. Practice. Improve."

    // Database
    const val DATABASE_NAME = "snaptutor_db"
    const val DATABASE_VERSION = 1

    // Simulated delays for fake implementations (milliseconds)
    const val FAKE_OCR_DELAY_MS = 1500L
    const val FAKE_LLM_DELAY_MS = 2000L
    const val FAKE_NETWORK_DELAY_MS = 1000L

    // Adaptation thresholds
    // PLACEHOLDER: These are provisional values, not a real algorithm.
    const val MASTERY_INCREASE_THRESHOLD = 0.70 // >70% → increase difficulty
    const val MASTERY_REINFORCE_THRESHOLD = 0.40 // <40% → reinforce concept
}
