package com.snaptutor.app.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
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
import com.snaptutor.app.core.ui.theme.StFocusLock
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceVariant
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel

/**
 * Actionable Navigable Card (Continue Learning).
 * Reserved rounded elevated container with press-scale feedback signaling tactile action.
 */
@Composable
fun ContinueLearningCard(
    topic: String,
    question: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .pressScale()
            .clip(RoundedCornerShape(16.dp))
            .background(StSurfaceVariant)
            .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(StFocusLock.copy(alpha = 0.12f))
                    .border(width = 1.dp, color = StFocusLock.copy(alpha = 0.3f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Resume problem",
                    tint = StFocusLock,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "RESUME FOCUS",
                    style = TelemetryLabel.copy(fontSize = 10.sp),
                    color = StFocusLock
                )
                Text(
                    text = topic,
                    style = MaterialTheme.typography.titleMedium,
                    color = StTextPrimary
                )
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodySmall,
                    color = StTextSecondary,
                    maxLines = 1
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = null,
                tint = StTextSecondary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun ContinueLearningCardPreview() {
    SnapTutorTheme {
        ContinueLearningCard(
            topic = "Linear Equations",
            question = "Solve 2x + 5 = 15",
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
