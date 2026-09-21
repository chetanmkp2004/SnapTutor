package com.snaptutor.app.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DocumentScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptutor.app.core.ui.components.PrimaryButton
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StFocusLock
import com.snaptutor.app.core.ui.theme.StLensPrimary
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceElevated
import com.snaptutor.app.core.ui.theme.StSurfaceVariant
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel

/**
 * Optical Extracted Problem Card.
 * Receives the text after reticle focus lock.
 */
@Composable
fun ExtractedTextCard(
    text: String,
    onTextChanged: (String) -> Unit,
    onLearnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(StSurfaceVariant)
            .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.DocumentScanner,
                    contentDescription = null,
                    tint = StFocusLock,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "EXTRACTED MATRIX",
                        style = TelemetryLabel.copy(fontSize = 9.sp),
                        color = StFocusLock
                    )
                    Text(
                        text = "Question to Solve",
                        style = MaterialTheme.typography.titleMedium,
                        color = StTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "e.g., Solve 2x + 5 = 15",
                        color = StTextSecondary.copy(alpha = 0.6f)
                    )
                },
                minLines = 3,
                maxLines = 5,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = StLensPrimary,
                    unfocusedBorderColor = StSurfaceBorder,
                    focusedTextColor = StTextPrimary,
                    unfocusedTextColor = StTextPrimary,
                    focusedContainerColor = StSurfaceElevated,
                    unfocusedContainerColor = StSurfaceElevated
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = "Explain & Learn Step-by-Step",
                onClick = onLearnClick,
                enabled = text.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun ExtractedTextCardPreview() {
    SnapTutorTheme {
        ExtractedTextCard(
            text = "Solve 2x + 5 = 15",
            onTextChanged = {},
            onLearnClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
