package com.snaptutor.app.sync

import com.snaptutor.app.data.models.SyncState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Placeholder SyncManager exposing a [StateFlow] of [SyncState].
 * No real network or Room writes in this phase.
 *
 * DEFERRED: Conflict resolution (offline edit vs. cloud edit) is unresolved
 * and deferred to a later phase. The strategy (last-write-wins, merge, user-prompt)
 * has not been decided.
 */
class SyncManager {

    private val _syncState = MutableStateFlow<SyncState>(SyncState.PendingItems(3))
    val syncState: StateFlow<SyncState> = _syncState.asStateFlow()

    /**
     * Simulates triggering a sync. No real network call.
     */
    suspend fun triggerSync() {
        _syncState.value = SyncState.Syncing
        // In a real implementation, this would:
        // 1. Query local Room DB for unsynced items
        // 2. Upload to Supabase/FastAPI
        // 3. Download new items from server
        // 4. Handle conflict resolution (DEFERRED)
        kotlinx.coroutines.delay(2000)
        _syncState.value = SyncState.Synced
    }

    fun getCurrentState(): SyncState = _syncState.value
}
