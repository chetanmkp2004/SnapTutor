# SnapTutor — Android Frontend Foundation

**SnapTutor** is an AI-powered Smart Education application built for the 30-hour iQOO Hackathon 2026. The app guides students through a complete learning loop:

```
Dashboard ──► Scan ──► Learn ──► Evaluation ──► Dashboard
```

---

## 🚀 Tech Stack

- **Platform:** Android Native (Single `:app` module)
- **Language:** Kotlin 2.4.20
- **UI Framework:** Jetpack Compose + Material 3 (Compose BOM 2026.09.00)
- **Navigation:** Jetpack Navigation Compose (v2.10.1) with type-safe `kotlinx.serialization` routes
- **Architecture:** MVVM + Clean Architecture with Repository pattern & `AppContainer` manual DI
- **State Management:** `UiState<T>` sealed class (`Loading`, `Success`, `Error`) with `StateFlow`
- **Database (Scaffold):** Android Room 2.8.5 with KSP
- **Networking (Scaffold):** Retrofit 3.0.0 + OkHttp 5.5.0 + kotlinx.serialization
- **Testing:** JUnit 4 + Kotlinx Coroutines Test

---

## 📱 Navigation & Flow

All routes are type-safe and defined in `Screen.kt`:

1. **Dashboard (`Screen.Dashboard`):**
   - Welcomes the student with current streak and learning metrics.
   - Quick action to scan or type a question.
   - `ContinueLearningCard` resumes recently viewed topics.
   - `WeakTopicCard` highlights review areas to boost mastery.
   - `SyncStatusCard` displays offline/cloud synchronization status.

2. **Scan (`Screen.Scan`):**
   - Mode 1: Simulated camera viewfinder with capture button triggering simulated on-device OCR.
   - Mode 2: Manual question typing and editing.
   - Triggers transition to Learn screen.

3. **Learn (`Screen.Learn(questionText)`):**
   - Displays step-by-step mathematical reasoning.
   - Highlights key concepts and curriculum/textbook references.
   - Indicates on-device AI generation.
   - "Practice Similar Questions" button navigates to Evaluation.

4. **Evaluation (`Screen.Evaluation(topic)`):**
   - Interactive multiple-choice quiz questions with progress indicator.
   - Immediate feedback and explanations upon submission.
   - Adaptive learning recommendation engine (`AdaptationManager`) computes next difficulty and suggested focus areas.
   - Seamless navigation back to Dashboard with updated student progress.

5. **Progress (`Screen.Progress`):**
   - Detailed progress analytics, topics mastered, and weak topic reviews.

---

## 🧩 Mock & Integration Contracts

Every integration point is backed by a clean interface with a mock/fake implementation:

| Domain | Interface | Fake Implementation | Real Service (Phase 2) |
|---|---|---|---|
| **Learning / Explanations** | `LearningRepository` | `FakeLearningRepository` | On-device Gemma / Cloud LLM |
| **Quiz & Evaluation** | `EvaluationRepository` | `FakeEvaluationRepository` | Dynamic RAG Question Bank |
| **Student Progress** | `ProgressRepository` | `FakeProgressRepository` | Room DB + Supabase Sync |
| **OCR Text Extraction** | `OcrProcessor` | `FakeOcrProcessor` | Google ML Kit Text Recognition |
| **Local Inference** | `LocalLlmManager` | `FakeLocalLlmManager` | On-device Edge AI |
| **Sync Management** | `SyncManager` | `SyncManager` (In-Memory) | WorkManager + Backend Sync |

All dependencies are wired in `AppContainer.kt`, providing a single swap point for real implementations without touching UI or ViewModel code.

---

## 🛠️ Build & Run Commands

```powershell
# Build Debug APK
.\gradlew.bat assembleDebug

# Run Unit Tests
.\gradlew.bat testDebugUnitTest
```