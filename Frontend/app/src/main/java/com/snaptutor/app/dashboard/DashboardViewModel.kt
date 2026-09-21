package com.snaptutor.app.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snaptutor.app.core.state.UiState
import com.snaptutor.app.data.models.SyncState
import com.snaptutor.app.data.repository.ProgressRepository
import com.snaptutor.app.sync.SyncManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val progressRepository: ProgressRepository,
    private val syncManager: SyncManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<DashboardData>>(UiState.Loading)
    val uiState: StateFlow<UiState<DashboardData>> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                progressRepository.getProgress()
                    .combine(syncManager.syncState) { progress, syncState ->
                        DashboardData(
                            progress = progress,
                            recentTopic = "Linear Equations",
                            recentQuestion = "Solve 2x + 5 = 15",
                            syncState = syncState
                        )
                    }
                    .collect { data ->
                        _uiState.value = UiState.Success(data)
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Failed to load dashboard")
            }
        }
    }

    fun triggerSync() {
        viewModelScope.launch {
            syncManager.triggerSync()
        }
    }
}
