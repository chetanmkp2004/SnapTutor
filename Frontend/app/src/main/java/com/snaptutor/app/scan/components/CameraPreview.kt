package com.snaptutor.app.scan.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CenterFocusStrong
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StBackground
import com.snaptutor.app.core.ui.theme.StFocusLock
import com.snaptutor.app.core.ui.theme.StLensPrimary
import com.snaptutor.app.core.ui.theme.StSurface
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel

/**
 * Optical Viewfinder Chamber (Motion Moment 2).
 *
 * Implements an authentic corner-bracket reticle that visibly 'locks' (scale pulse
 * and color flash to StFocusLock) upon capture, before transitioning into OCR extracted text.
 */
@Composable
fun CameraPreview(
    isProcessing: Boolean,
    modifier: Modifier = Modifier
) {
    // Reticle Focus-Lock Motion (Motion Moment 2)
    // Scale tightens from 1.0f -> 0.94f on capture, color flashes from neutral to StFocusLock
    val reticleScale by animateFloatAsState(
        targetValue = if (isProcessing) 0.94f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "reticleFocusScale"
    )

    val reticleColor by animateColorAsState(
        targetValue = if (isProcessing) StFocusLock else StLensPrimary.copy(alpha = 0.8f),
        animationSpec = tween(durationMillis = 250),
        label = "reticleColorPulse"
    )

    // Laser scan sweep when processing
    val infiniteTransition = rememberInfiniteTransition(label = "scanLaserTransition")
    val laserProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laserSweep"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(290.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(StSurface)
            .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Technical Optical Grid / Reticle Layer with Focus Lock pulse
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = reticleScale
                    scaleY = reticleScale
                }
        ) {
            val padding = 20.dp.toPx()
            val bracketLength = 28.dp.toPx()
            val stroke = 3.dp.toPx()

            // ── 4 Corner Reticle Brackets (L-shapes) ──
            // Top-Left
            drawLine(
                color = reticleColor,
                start = Offset(padding, padding),
                end = Offset(padding + bracketLength, padding),
                strokeWidth = stroke,
                cap = StrokeCap.Square
            )
            drawLine(
                color = reticleColor,
                start = Offset(padding, padding),
                end = Offset(padding, padding + bracketLength),
                strokeWidth = stroke,
                cap = StrokeCap.Square
            )

            // Top-Right
            drawLine(
                color = reticleColor,
                start = Offset(size.width - padding, padding),
                end = Offset(size.width - padding - bracketLength, padding),
                strokeWidth = stroke,
                cap = StrokeCap.Square
            )
            drawLine(
                color = reticleColor,
                start = Offset(size.width - padding, padding),
                end = Offset(size.width - padding, padding + bracketLength),
                strokeWidth = stroke,
                cap = StrokeCap.Square
            )

            // Bottom-Left
            drawLine(
                color = reticleColor,
                start = Offset(padding, size.height - padding),
                end = Offset(padding + bracketLength, size.height - padding),
                strokeWidth = stroke,
                cap = StrokeCap.Square
            )
            drawLine(
                color = reticleColor,
                start = Offset(padding, size.height - padding),
                end = Offset(padding, size.height - padding - bracketLength),
                strokeWidth = stroke,
                cap = StrokeCap.Square
            )

            // Bottom-Right
            drawLine(
                color = reticleColor,
                start = Offset(size.width - padding, size.height - padding),
                end = Offset(size.width - padding - bracketLength, size.height - padding),
                strokeWidth = stroke,
                cap = StrokeCap.Square
            )
            drawLine(
                color = reticleColor,
                start = Offset(size.width - padding, size.height - padding),
                end = Offset(size.width - padding, size.height - padding - bracketLength),
                strokeWidth = stroke,
                cap = StrokeCap.Square
            )

            // ── Subtle Optical Center Crosshair ──
            val centerX = size.width / 2
            val centerY = size.height / 2
            val crosshairSize = 8.dp.toPx()
            val crosshairColor = reticleColor.copy(alpha = 0.5f)

            drawLine(
                color = crosshairColor,
                start = Offset(centerX - crosshairSize, centerY),
                end = Offset(centerX + crosshairSize, centerY),
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawLine(
                color = crosshairColor,
                start = Offset(centerX, centerY - crosshairSize),
                end = Offset(centerX, centerY + crosshairSize),
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )

            // ── Laser Sweep Line (Active only during processing) ──
            if (isProcessing) {
                val laserY = padding + (size.height - 2 * padding) * laserProgress
                drawLine(
                    color = StFocusLock.copy(alpha = 0.85f),
                    start = Offset(padding + 4.dp.toPx(), laserY),
                    end = Offset(size.width - padding - 4.dp.toPx(), laserY),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        // Viewfinder Telemetry UI overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Telemetry Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isProcessing) StFocusLock else StLensPrimary)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isProcessing) "LOCK: ACQUIRED" else "OPTICS: ACTIVE",
                        style = TelemetryLabel.copy(fontSize = 10.sp),
                        color = if (isProcessing) StFocusLock else StTextSecondary
                    )
                }

                Text(
                    text = "FOV 100% // ISO AUTO",
                    style = TelemetryLabel.copy(fontSize = 9.sp),
                    color = StTextSecondary
                )
            }

            // Center Guidance Message
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isProcessing) {
                    Text(
                        text = "FOCUS LOCKED",
                        style = TelemetryLabel.copy(fontSize = 12.sp),
                        color = StFocusLock
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Extracting problem matrix via on-device OCR...",
                        style = MaterialTheme.typography.bodySmall,
                        color = StTextPrimary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.CenterFocusStrong,
                        contentDescription = "Target",
                        tint = StTextSecondary.copy(alpha = 0.4f),
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Align question within reticle brackets",
                        style = MaterialTheme.typography.titleSmall,
                        color = StTextPrimary
                    )
                    Text(
                        text = "Tap shutter below to trigger focus lock & OCR",
                        style = MaterialTheme.typography.bodySmall,
                        color = StTextSecondary
                    )
                }
            }

            // Bottom Telemetry Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "[ LENS: 24MM ]",
                    style = TelemetryLabel.copy(fontSize = 9.sp),
                    color = StTextSecondary
                )
                Text(
                    text = if (isProcessing) "[ PROCESSING ]" else "[ READY ]",
                    style = TelemetryLabel.copy(fontSize = 9.sp),
                    color = if (isProcessing) StFocusLock else StLensPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun CameraPreviewPreview() {
    SnapTutorTheme {
        CameraPreview(
            isProcessing = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}
