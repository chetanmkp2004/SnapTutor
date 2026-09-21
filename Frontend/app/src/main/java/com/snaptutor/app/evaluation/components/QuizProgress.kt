package com.snaptutor.app.evaluation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StPurple
import com.snaptutor.app.data.models.Difficulty

@Composable
fun QuizProgress(
    currentIndex: Int,
    totalCount: Int,
    difficulty: Difficulty,
    modifier: Modifier = Modifier
) {
    val progress = if (totalCount > 0) (currentIndex + 1).toFloat() / totalCount else 0f

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question ${currentIndex + 1} of $totalCount",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = difficulty.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = StPurple,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = StPurple,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0F)
@Composable
private fun QuizProgressPreview() {
    SnapTutorTheme {
        QuizProgress(
            currentIndex = 1,
            totalCount = 3,
            difficulty = Difficulty.MEDIUM,
            modifier = Modifier.padding(16.dp)
        )
    }
}
