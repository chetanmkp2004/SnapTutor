package com.snaptutor.app.evaluation.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StError
import com.snaptutor.app.core.ui.theme.StPurple
import com.snaptutor.app.core.ui.theme.StSuccess

@Composable
fun AnswerOption(
    optionIndex: Int,
    optionText: String,
    isSelected: Boolean,
    isSubmitted: Boolean,
    isCorrectAnswer: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val optionLabel = ('A' + optionIndex).toString()

    val (borderColor, containerColor, labelBgColor, labelTextColor) = when {
        isSubmitted && isCorrectAnswer -> Quad(
            StSuccess,
            StSuccess.copy(alpha = 0.12f),
            StSuccess,
            Color.White
        )
        isSubmitted && isSelected && !isCorrectAnswer -> Quad(
            StError,
            StError.copy(alpha = 0.12f),
            StError,
            Color.White
        )
        isSelected -> Quad(
            StPurple,
            StPurple.copy(alpha = 0.15f),
            StPurple,
            Color.White
        )
        else -> Quad(
            MaterialTheme.colorScheme.outline,
            MaterialTheme.colorScheme.surfaceContainer,
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !isSubmitted, onClick = onSelect)
            .border(
                width = if (isSelected || (isSubmitted && isCorrectAnswer)) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(labelBgColor),
                contentAlignment = Alignment.Center
            ) {
                if (isSubmitted && isCorrectAnswer) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Correct",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else if (isSubmitted && isSelected && !isCorrectAnswer) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Incorrect",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = optionLabel,
                        style = MaterialTheme.typography.labelLarge,
                        color = labelTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = optionText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0F)
@Composable
private fun AnswerOptionPreview() {
    SnapTutorTheme {
        AnswerOption(
            optionIndex = 0,
            optionText = "x = 5",
            isSelected = true,
            isSubmitted = false,
            isCorrectAnswer = false,
            onSelect = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
