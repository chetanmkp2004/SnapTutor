package com.snaptutor.app.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptutor.app.core.ui.modifiers.pressScale
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StLensPrimary
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceVariant
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel
import com.snaptutor.app.scan.InputMode

/**
 * Technical Optical Mode Switcher (Viewfinder vs. Manual Text Input).
 */
@Composable
fun InputMethodSelector(
    selectedMode: InputMode,
    onModeSelected: (InputMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(StSurfaceVariant)
            .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(14.dp))
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Camera tab
            val cameraSelected = selectedMode == InputMode.CAMERA
            Box(
                modifier = Modifier
                    .weight(1f)
                    .pressScale()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (cameraSelected) StLensPrimary
                        else Color.Transparent
                    )
                    .clickable { onModeSelected(InputMode.CAMERA) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.CameraAlt,
                        contentDescription = "Camera Viewfinder",
                        tint = if (cameraSelected) Color.White else StTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "VIEWFINDER",
                        style = TelemetryLabel.copy(fontSize = 11.sp),
                        color = if (cameraSelected) Color.White else StTextSecondary
                    )
                }
            }

            // Type tab
            val typeSelected = selectedMode == InputMode.TEXT
            Box(
                modifier = Modifier
                    .weight(1f)
                    .pressScale()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (typeSelected) StLensPrimary
                        else Color.Transparent
                    )
                    .clickable { onModeSelected(InputMode.TEXT) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Direct Input",
                        tint = if (typeSelected) Color.White else StTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "MANUAL INPUT",
                        style = TelemetryLabel.copy(fontSize = 11.sp),
                        color = if (typeSelected) Color.White else StTextSecondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun InputMethodSelectorPreview() {
    SnapTutorTheme {
        InputMethodSelector(
            selectedMode = InputMode.CAMERA,
            onModeSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
