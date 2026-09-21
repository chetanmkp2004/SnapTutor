package com.snaptutor.app.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudDone
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.CloudQueue
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptutor.app.core.ui.modifiers.pressScale
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StError
import com.snaptutor.app.core.ui.theme.StOfflineIndicator
import com.snaptutor.app.core.ui.theme.StOnlineIndicator
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceVariant
import com.snaptutor.app.core.ui.theme.StSyncingIndicator
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel
import com.snaptutor.app.data.models.SyncState

/**
 * Cloud Sync Telemetry Panel.
 * Uses tone-driven surface hierarchy with status telemetry badges.
 */
@Composable
fun SyncStatusCard(
    syncState: SyncState,
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (icon, tint, title, subtitle) = when (syncState) {
        is SyncState.Synced -> Quadruple(
            Icons.Rounded.CloudDone,
            StOnlineIndicator,
            "Cloud Synced",
            "Telemetry records up to date"
        )
        is SyncState.Syncing -> Quadruple(
            Icons.Rounded.Sync,
            StSyncingIndicator,
            "Syncing Telemetry",
            "Streaming progress buffer to cloud"
        )
        is SyncState.PendingItems -> Quadruple(
            Icons.Rounded.CloudQueue,
            StOfflineIndicator,
            "Offline Mode",
            "${syncState.count} item(s) cached locally"
        )
        is SyncState.Failed -> Quadruple(
            Icons.Rounded.CloudOff,
            StError,
            "Sync Suspended",
            syncState.message
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(StSurfaceVariant)
            .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.12f))
                    .border(width = 1.dp, color = tint.copy(alpha = 0.3f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = tint,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "STATUS",
                    style = TelemetryLabel.copy(fontSize = 9.sp),
                    color = tint
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = StTextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = StTextSecondary
                )
            }

            IconButton(
                onClick = onSyncClick,
                enabled = syncState !is SyncState.Syncing,
                modifier = Modifier.pressScale()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Sync,
                    contentDescription = "Sync now",
                    tint = if (syncState is SyncState.Syncing) {
                        StTextSecondary.copy(alpha = 0.38f)
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            }
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun SyncStatusCardPreview() {
    SnapTutorTheme {
        SyncStatusCard(
            syncState = SyncState.Synced,
            onSyncClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
