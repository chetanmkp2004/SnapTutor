package com.snaptutor.app.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snaptutor.app.data.models.SyncState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SyncViewModel(
    private val syncManager: SyncManager
) : ViewModel() {

    val syncState: StateFlow<SyncState> = syncManager.syncState
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SyncState.PendingItems(0))

    fun triggerSync() {
        viewModelScope.launch {
            syncManager.triggerSync()
        }
    }
}
