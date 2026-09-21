package com.snaptutor.app.dashboard.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.CenterFocusStrong
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptutor.app.core.ui.modifiers.pressScale
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StFocusLock
import com.snaptutor.app.core.ui.theme.StLensPrimary
import com.snaptutor.app.core.ui.theme.StSurface
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceElevated
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel

/**
 * Hero Viewfinder Card (Differentiated Structural Hero).
 *
 * Structurally distinct from stat cards and list rows. Uses an authentic optical viewfinder
 * framing language with corner brackets, crosshairs, and a tactile shutter-style CTA.
 */
@Composable
fun HeroViewfinderCard(
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .pressScale()
            .clip(RoundedCornerShape(18.dp))
            .background(StSurface)
            .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(18.dp))
            .clickable(onClick = onScanClick)
    ) {
        // Optical corner brackets overlay
        Canvas(modifier = Modifier.matchParentSize()) {
            val bracketLength = 16.dp.toPx()
            val bracketStroke = 2.dp.toPx()
            val bracketColor = StLensPrimary.copy(alpha = 0.85f)
            val padding = 12.dp.toPx()

            // Top-left bracket
            drawLine(
                color = bracketColor,
                start = Offset(padding, padding),
                end = Offset(padding + bracketLength, padding),
                strokeWidth = bracketStroke,
                cap = StrokeCap.Round
            )
            drawLine(
                color = bracketColor,
                start = Offset(padding, padding),
                end = Offset(padding, padding + bracketLength),
                strokeWidth = bracketStroke,
                cap = StrokeCap.Round
            )

            // Top-right bracket
            drawLine(
                color = bracketColor,
                start = Offset(size.width - padding, padding),
                end = Offset(size.width - padding - bracketLength, padding),
                strokeWidth = bracketStroke,
                cap = StrokeCap.Round
            )
            drawLine(
                color = bracketColor,
                start = Offset(size.width - padding, padding),
                end = Offset(size.width - padding, padding + bracketLength),
                strokeWidth = bracketStroke,
                cap = StrokeCap.Round
            )

            // Bottom-left bracket
            drawLine(
                color = bracketColor,
                start = Offset(padding, size.height - padding),
                end = Offset(padding + bracketLength, size.height - padding),
                strokeWidth = bracketStroke,
                cap = StrokeCap.Round
            )
            drawLine(
                color = bracketColor,
                start = Offset(padding, size.height - padding),
                end = Offset(padding, size.height - padding - bracketLength),
                strokeWidth = bracketStroke,
                cap = StrokeCap.Round
            )

            // Bottom-right bracket
            drawLine(
                color = bracketColor,
                start = Offset(size.width - padding, size.height - padding),
                end = Offset(size.width - padding - bracketLength, size.height - padding),
                strokeWidth = bracketStroke,
                cap = StrokeCap.Round
            )
            drawLine(
                color = bracketColor,
                start = Offset(size.width - padding, size.height - padding),
                end = Offset(size.width - padding, size.height - padding - bracketLength),
                strokeWidth = bracketStroke,
                cap = StrokeCap.Round
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            // Optical mode label
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.CenterFocusStrong,
                        contentDescription = null,
                        tint = StFocusLock,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "OPTICAL SOLVER // ON-DEVICE",
                        style = TelemetryLabel.copy(fontSize = 10.sp),
                        color = StFocusLock
                    )
                }

                Text(
                    text = "RETICLE 1.0",
                    style = TelemetryLabel.copy(fontSize = 9.sp),
                    color = StTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Snap Any Question",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                color = StTextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Align any math or science problem in the viewfinder. On-device OCR resolves the solution step-by-step.",
                style = MaterialTheme.typography.bodySmall,
                color = StTextSecondary
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Shutter style scan trigger
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(StSurfaceElevated)
                    .border(width = 1.dp, color = StLensPrimary.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(StLensPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Camera,
                            contentDescription = "Capture",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "OPEN VIEWFINDER",
                        style = TelemetryLabel.copy(fontSize = 11.sp),
                        color = StTextPrimary
                    )
                }

                Text(
                    text = "[ TAP TO SCAN ]",
                    style = TelemetryLabel.copy(fontSize = 10.sp),
                    color = StLensPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun HeroViewfinderCardPreview() {
    SnapTutorTheme {
        HeroViewfinderCard(
            onScanClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
