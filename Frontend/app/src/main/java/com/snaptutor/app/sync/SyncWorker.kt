package com.snaptutor.app.sync

/**
 * WorkManager scaffold for background sync.
 * NOT scheduled in this phase — placeholder class only.
 * Real implementation will use WorkManager PeriodicWorkRequest with
 * network connectivity constraints.
 */
class SyncWorker {
    // TODO: Extend CoroutineWorker when WorkManager dependency is added
    // TODO: Schedule with Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
    // TODO: Handle conflict resolution (DEFERRED — strategy not yet decided)

    companion object {
        const val WORK_NAME = "snaptutor_sync"
        const val SYNC_INTERVAL_HOURS = 1L
    }
}
