package com.snaptutor.app.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snaptutor.app.core.ui.theme.SnapTutorTheme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.snaptutor.app.core.ui.modifiers.shimmerPlaceholder

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    message: String = "Calibrating optical model…"
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Status header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .shimmerPlaceholder(shape = RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Hero viewfinder skeleton
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .shimmerPlaceholder(shape = RoundedCornerShape(16.dp))
        )

        // Stat row skeleton
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp)
                    .shimmerPlaceholder(shape = RoundedCornerShape(14.dp))
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp)
                    .shimmerPlaceholder(shape = RoundedCornerShape(14.dp))
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp)
                    .shimmerPlaceholder(shape = RoundedCornerShape(14.dp))
            )
        }

        // Actionable cards skeletons
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .shimmerPlaceholder(shape = RoundedCornerShape(14.dp))
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .shimmerPlaceholder(shape = RoundedCornerShape(14.dp))
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0F)
@Composable
private fun LoadingViewPreview() {
    SnapTutorTheme {
        LoadingView()
    }
}
