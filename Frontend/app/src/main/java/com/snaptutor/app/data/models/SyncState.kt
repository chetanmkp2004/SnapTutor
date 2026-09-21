package com.snaptutor.app.data.models

/**
 * Represents the current synchronization state between device and cloud.
 */
sealed class SyncState {
    data object Synced : SyncState()
    data object Syncing : SyncState()
    data class PendingItems(val count: Int) : SyncState()
    data class Failed(val message: String) : SyncState()
}
