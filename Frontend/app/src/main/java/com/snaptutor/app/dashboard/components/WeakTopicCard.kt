package com.snaptutor.app.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CenterFocusWeak
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptutor.app.core.ui.modifiers.pressScale
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StCalibratedAmber
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceElevated
import com.snaptutor.app.core.ui.theme.StSurfaceVariant
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel

/**
 * Actionable Weak Topics Review Panel.
 * Tone-based elevated surface with keyed topic chips and press-scale tactile feedback.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeakTopicCard(
    weakTopics: List<String>,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(StSurfaceVariant)
            .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(16.dp))
            .padding(18.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(StCalibratedAmber.copy(alpha = 0.12f))
                        .border(width = 1.dp, color = StCalibratedAmber.copy(alpha = 0.3f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CenterFocusWeak,
                        contentDescription = "Review Topics",
                        tint = StCalibratedAmber,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "CALIBRATION QUEUE",
                        style = TelemetryLabel.copy(fontSize = 10.sp),
                        color = StCalibratedAmber
                    )
                    Text(
                        text = "Topics to Review",
                        style = MaterialTheme.typography.titleMedium,
                        color = StTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (weakTopics.isEmpty()) {
                Text(
                    text = "All optical topics calibrated. No weak areas detected.",
                    style = MaterialTheme.typography.bodySmall,
                    color = StTextSecondary
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    weakTopics.forEach { topic ->
                        key(topic) {
                            Box(
                                modifier = Modifier
                                    .pressScale()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(StSurfaceElevated)
                                    .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(12.dp))
                                    .clickable { onTopicClick(topic) }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = topic,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = StTextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                        contentDescription = null,
                                        tint = StCalibratedAmber,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun WeakTopicCardPreview() {
    SnapTutorTheme {
        WeakTopicCard(
            weakTopics = listOf("Quadratic Equations", "Word Problems", "Trigonometry"),
            onTopicClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
