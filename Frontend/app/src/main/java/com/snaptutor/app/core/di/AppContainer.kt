package com.snaptutor.app.core.di

import com.snaptutor.app.ai.ocr.FakeOcrProcessor
import com.snaptutor.app.ai.ocr.OcrProcessor
import com.snaptutor.app.ai.ondevice.FakeLocalLlmManager
import com.snaptutor.app.ai.ondevice.LocalLlmManager
import com.snaptutor.app.data.repository.EvaluationRepository
import com.snaptutor.app.data.repository.FakeEvaluationRepository
import com.snaptutor.app.data.repository.FakeLearningRepository
import com.snaptutor.app.data.repository.FakeProgressRepository
import com.snaptutor.app.data.repository.LearningRepository
import com.snaptutor.app.data.repository.ProgressRepository
import com.snaptutor.app.sync.SyncManager

/**
 * Dependency container providing all repositories, AI services, and sync managers.
 * Single point of replacement for real implementations when transitioning from mock phase.
 */
interface AppContainer {
    val learningRepository: LearningRepository
    val evaluationRepository: EvaluationRepository
    val progressRepository: ProgressRepository
    val ocrProcessor: OcrProcessor
    val localLlmManager: LocalLlmManager
    val syncManager: SyncManager
}

class DefaultAppContainer : AppContainer {
    override val learningRepository: LearningRepository by lazy { FakeLearningRepository() }
    override val evaluationRepository: EvaluationRepository by lazy { FakeEvaluationRepository() }
    override val progressRepository: ProgressRepository by lazy { FakeProgressRepository() }
    override val ocrProcessor: OcrProcessor by lazy { FakeOcrProcessor() }
    override val localLlmManager: LocalLlmManager by lazy { FakeLocalLlmManager() }
    override val syncManager: SyncManager by lazy { SyncManager() }
}
