package com.snaptutor.app.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snaptutor.app.core.ui.modifiers.pressScale
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StBackground
import com.snaptutor.app.core.ui.theme.StProgressTeal
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceElevated
import com.snaptutor.app.core.ui.theme.StSurfaceVariant
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel
import com.snaptutor.app.dashboard.components.ProgressCard
import com.snaptutor.app.dashboard.components.WeakTopicCard
import com.snaptutor.app.data.models.StudentProgress
import com.snaptutor.app.data.repository.ProgressRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    progressRepository: ProgressRepository,
    onNavigateToLearn: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress by progressRepository.getProgress().collectAsStateWithLifecycle(initialValue = StudentProgress())
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = StBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "TELEMETRY & MASTERY",
                            style = TelemetryLabel.copy(fontSize = 10.sp),
                            color = StProgressTeal
                        )
                        Text(
                            text = "Learning Progress",
                            style = MaterialTheme.typography.titleLarge,
                            color = StTextPrimary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = StTextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Open Telemetry Card with Animated Mastery Ring
            ProgressCard(progress = progress, modifier = Modifier.fillMaxWidth())

            // Topics Studied Section (Keyed items, tonal surface, press-scale)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(StSurfaceVariant)
                    .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(StProgressTeal.copy(alpha = 0.12f))
                                .border(width = 1.dp, color = StProgressTeal.copy(alpha = 0.3f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = null,
                                tint = StProgressTeal,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "VERIFIED CONCEPTS",
                                style = TelemetryLabel.copy(fontSize = 9.sp),
                                color = StProgressTeal
                            )
                            Text(
                                text = "Topics Covered (${progress.topicsStudied.size})",
                                style = MaterialTheme.typography.titleMedium,
                                color = StTextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    progress.topicsStudied.forEach { topic ->
                        key(topic) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .pressScale()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(StSurfaceElevated)
                                    .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(12.dp))
                                    .clickable { onNavigateToLearn("Questions on $topic") }
                                    .padding(horizontal = 14.dp, vertical = 12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = topic,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = StTextPrimary
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                        contentDescription = "Review topic",
                                        tint = StTextSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Topics to review
            WeakTopicCard(
                weakTopics = progress.weakTopics,
                onTopicClick = { topic -> onNavigateToLearn("Questions on $topic") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun ProgressScreenPreview() {
    SnapTutorTheme {
        // Preview
    }
}
