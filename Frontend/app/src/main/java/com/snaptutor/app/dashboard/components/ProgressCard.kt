package com.snaptutor.app.dashboard.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptutor.app.core.ui.theme.MetricDisplayLarge
import com.snaptutor.app.core.ui.theme.MetricDisplayMedium
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StCalibratedAmber
import com.snaptutor.app.core.ui.theme.StDivider
import com.snaptutor.app.core.ui.theme.StProgressTeal
import com.snaptutor.app.core.ui.theme.StSurface
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel
import com.snaptutor.app.data.models.StudentProgress

/**
 * ProgressTelemetrySection (Differentiated Stat Display).
 *
 * Replaces generic nested card chrome with an open, high-legibility telemetry panel.
 * Contains Motion Moment 1: An orchestrated reveal on first composition where the circular
 * mastery ring sweeps smoothly from 0 to target value and stat numbers count up concurrently.
 * Once settled, it halts redraws (zero ongoing frame burn).
 */
@Composable
fun ProgressCard(
    progress: StudentProgress,
    modifier: Modifier = Modifier
) {
    var isRevealed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isRevealed = true
    }

    // Orchestrated Entrance Reveal (Motion Moment 1)
    val animatedProgress by animateFloatAsState(
        targetValue = if (isRevealed) progress.overallMastery.toFloat() else 0f,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "masteryRingSweep"
    )

    val animatedStreak by animateIntAsState(
        targetValue = if (isRevealed) progress.streakDays else 0,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "streakCounter"
    )

    val animatedCorrect by animateIntAsState(
        targetValue = if (isRevealed) progress.totalCorrect else 0,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "correctCounter"
    )

    val animatedTopics by animateIntAsState(
        targetValue = if (isRevealed) progress.topicsStudied.size else 0,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "topicsCounter"
    )

    // Open structural container — bare stats, tonal surface, hairline border, no shadow
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = StSurfaceBorder,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular Mastery Ring with centered display metric
            MasteryRing(
                progress = animatedProgress,
                displayPercentage = (animatedProgress * 100).toInt(),
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            // Hairline Vertical Divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .border(width = 0.5.dp, color = StDivider)
            )

            Spacer(modifier = Modifier.width(20.dp))

            // Bare numbers with labels — high visual weight contrast
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BareStatItem(
                        label = "STREAK",
                        value = "${animatedStreak}d",
                        valueColor = StCalibratedAmber
                    )
                    BareStatItem(
                        label = "STUDIED",
                        value = "$animatedTopics",
                        valueColor = StTextPrimary
                    )
                    BareStatItem(
                        label = "CORRECT",
                        value = "$animatedCorrect/${progress.totalQuestionsAttempted}",
                        valueColor = StProgressTeal
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "TARGET: 80% MASTERY FOR ADVANCED LEVEL",
                    style = TelemetryLabel.copy(fontSize = 9.sp),
                    color = StTextSecondary
                )
            }
        }
    }
}

/**
 * Circular Mastery Ring drawn on Canvas.
 * Uses finite fast-out-slow-in sweep; 0 frame burn when settled.
 */
@Composable
private fun MasteryRing(
    progress: Float,
    displayPercentage: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val trackColor = StDivider
        val ringColor = StProgressTeal

        Canvas(modifier = Modifier.size(90.dp)) {
            val strokeWidth = 8.dp.toPx()

            // Background full track
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Dynamic progress sweep
            drawArc(
                color = ringColor,
                startAngle = -90f,
                sweepAngle = progress * 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Centered display number
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$displayPercentage%",
                style = MetricDisplayLarge.copy(fontSize = 22.sp, lineHeight = 24.sp),
                color = StTextPrimary
            )
            Text(
                text = "MASTERY",
                style = TelemetryLabel.copy(fontSize = 8.sp, lineHeight = 10.sp),
                color = StTextSecondary
            )
        }
    }
}

@Composable
private fun BareStatItem(
    label: String,
    value: String,
    valueColor: Color = StTextPrimary
) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = value,
            style = MetricDisplayMedium.copy(fontSize = 20.sp, lineHeight = 22.sp),
            color = valueColor
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = TelemetryLabel.copy(fontSize = 10.sp),
            color = StTextSecondary
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun ProgressCardPreview() {
    SnapTutorTheme {
        ProgressCard(
            progress = StudentProgress(
                totalQuestionsAttempted = 24,
                totalCorrect = 18,
                topicsStudied = listOf("Linear Equations", "Algebra"),
                streakDays = 3
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
