package com.snaptutor.app.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snaptutor.app.core.state.UiState
import com.snaptutor.app.core.ui.components.ErrorView
import com.snaptutor.app.core.ui.components.LoadingView
import com.snaptutor.app.core.ui.modifiers.pressScale
import com.snaptutor.app.core.ui.theme.MetricDisplaySmall
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StBackground
import com.snaptutor.app.core.ui.theme.StCalibratedAmber
import com.snaptutor.app.core.ui.theme.StLensPrimary
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceElevated
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel
import com.snaptutor.app.dashboard.components.ContinueLearningCard
import com.snaptutor.app.dashboard.components.HeroViewfinderCard
import com.snaptutor.app.dashboard.components.ProgressCard
import com.snaptutor.app.dashboard.components.SyncStatusCard
import com.snaptutor.app.dashboard.components.WeakTopicCard
import com.snaptutor.app.data.models.StudentProgress
import com.snaptutor.app.data.models.SyncState

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToScan: () -> Unit,
    onNavigateToLearn: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = StBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToScan,
                containerColor = StLensPrimary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                modifier = Modifier
                    .pressScale()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.CameraAlt,
                    contentDescription = "Open Viewfinder",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is UiState.Loading -> {
                LoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    message = "Calibrating optical telemetry..."
                )
            }

            is UiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.loadDashboard() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is UiState.Success -> {
                val data = state.data
                DashboardContent(
                    data = data,
                    onScanClick = onNavigateToScan,
                    onContinueClick = { onNavigateToLearn(data.recentQuestion) },
                    onWeakTopicClick = { topic -> onNavigateToLearn("Questions on $topic") },
                    onSyncClick = { viewModel.triggerSync() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun DashboardContent(
    data: DashboardData,
    onScanClick: () -> Unit,
    onContinueClick: () -> Unit,
    onWeakTopicClick: (String) -> Unit,
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // App Header: Technical calibration branding + streak telemetry pill
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "SnapTutor",
                        style = MaterialTheme.typography.headlineMedium,
                        color = StTextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(StLensPrimary.copy(alpha = 0.15f))
                            .border(width = 0.5.dp, color = StLensPrimary.copy(alpha = 0.4f), shape = RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "OPTICS CORE",
                            style = TelemetryLabel.copy(fontSize = 9.sp),
                            color = StLensPrimary
                        )
                    }
                }
                Text(
                    text = "On-device optical reasoning & mastery",
                    style = MaterialTheme.typography.bodySmall,
                    color = StTextSecondary
                )
            }

            // Streak Telemetry Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(StSurfaceElevated)
                    .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.LocalFireDepartment,
                        contentDescription = "Streak days",
                        tint = StCalibratedAmber,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${data.progress.streakDays}d",
                        style = MetricDisplaySmall.copy(fontSize = 14.sp),
                        color = StTextPrimary
                    )
                }
            }
        }

        // Hero 1: Structurally distinct Optical Viewfinder Hero Card
        HeroViewfinderCard(
            onScanClick = onScanClick,
            modifier = Modifier.fillMaxWidth()
        )

        // Hero 2: Open Telemetry Stats with Animated Mastery Ring (Motion Moment 1)
        ProgressCard(
            progress = data.progress,
            modifier = Modifier.fillMaxWidth()
        )

        // Actionable Navigable Card: Continue Learning
        ContinueLearningCard(
            topic = data.recentTopic,
            question = data.recentQuestion,
            onClick = onContinueClick,
            modifier = Modifier.fillMaxWidth()
        )

        // Actionable Navigable Card: Topics to Review
        WeakTopicCard(
            weakTopics = data.progress.weakTopics,
            onTopicClick = onWeakTopicClick,
            modifier = Modifier.fillMaxWidth()
        )

        // Telemetry Card: Sync Status
        SyncStatusCard(
            syncState = data.syncState,
            onSyncClick = onSyncClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(72.dp)) // Padding for FAB
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun DashboardScreenPreview() {
    SnapTutorTheme {
        DashboardContent(
            data = DashboardData(
                progress = StudentProgress(
                    totalQuestionsAttempted = 24,
                    totalCorrect = 18,
                    topicsStudied = listOf("Linear Equations", "Quadratic Equations"),
                    weakTopics = listOf("Quadratic Equations", "Word Problems"),
                    streakDays = 3,
                    lastStudiedTimestamp = System.currentTimeMillis()
                ),
                recentTopic = "Linear Equations",
                recentQuestion = "Solve 2x + 5 = 15",
                syncState = SyncState.Synced
            ),
            onScanClick = {},
            onContinueClick = {},
            onWeakTopicClick = {},
            onSyncClick = {}
        )
    }
}
