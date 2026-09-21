package com.snaptutor.app.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snaptutor.app.core.ui.modifiers.pressScale
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StFocusLock
import com.snaptutor.app.core.ui.theme.StLensPrimary
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceElevated

/**
 * Technical Optical Shutter Trigger.
 * Features concentric lens rings, press-scale tactile feedback, and focal lock cues.
 */
@Composable
fun CaptureButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(80.dp)
            .pressScale(interactionSource = interactionSource, enabled = enabled)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = if (enabled) StFocusLock.copy(alpha = 0.6f) else StSurfaceBorder,
                shape = CircleShape
            )
            .padding(4.dp)
            .border(
                width = 1.dp,
                color = if (enabled) StLensPrimary else StSurfaceBorder,
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true, radius = 38.dp),
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(
                    if (enabled) StLensPrimary
                    else StSurfaceElevated
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Camera,
                contentDescription = "Capture Question",
                tint = if (enabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun CaptureButtonPreview() {
    SnapTutorTheme {
        CaptureButton(onClick = {})
    }
}
