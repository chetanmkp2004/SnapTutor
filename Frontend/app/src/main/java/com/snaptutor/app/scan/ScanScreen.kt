package com.snaptutor.app.scan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.core.ui.theme.StBackground
import com.snaptutor.app.core.ui.theme.StFocusLock
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceVariant
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary
import com.snaptutor.app.core.ui.theme.TelemetryLabel
import com.snaptutor.app.scan.components.CameraPreview
import com.snaptutor.app.scan.components.CaptureButton
import com.snaptutor.app.scan.components.ExtractedTextCard
import com.snaptutor.app.scan.components.InputMethodSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    viewModel: ScanViewModel,
    onNavigateToLearn: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = StBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "OPTICAL VIEWFINDER",
                            style = TelemetryLabel.copy(fontSize = 10.sp),
                            color = StFocusLock
                        )
                        Text(
                            text = "Scan Problem",
                            style = MaterialTheme.typography.titleLarge,
                            color = StTextPrimary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = StTextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StBackground
                )
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Camera / Type switcher
            InputMethodSelector(
                selectedMode = uiState.inputMode,
                onModeSelected = { viewModel.setInputMode(it) }
            )

            // Permissions / offline privacy notice
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(StSurfaceVariant)
                    .border(width = 1.dp, color = StSurfaceBorder, shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Shield,
                        contentDescription = null,
                        tint = StFocusLock,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = "ON-DEVICE OCR: Text is extracted strictly on hardware. No image upload.",
                        style = TelemetryLabel.copy(fontSize = 9.sp),
                        color = StTextSecondary
                    )
                }
            }

            if (uiState.inputMode == InputMode.CAMERA) {
                // Viewfinder with authentic corner-bracket reticle & focus lock pulse (Motion Moment 2)
                CameraPreview(
                    isProcessing = uiState.isProcessing,
                    modifier = Modifier.fillMaxWidth()
                )

                CaptureButton(
                    onClick = { viewModel.captureAndExtract() },
                    enabled = !uiState.isProcessing
                )

                // Smooth reveal transition into extracted text upon capture lock
                AnimatedVisibility(
                    visible = uiState.extractedText.isNotBlank(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    ExtractedTextCard(
                        text = uiState.extractedText,
                        onTextChanged = { viewModel.onExtractedTextChanged(it) },
                        onLearnClick = { onNavigateToLearn(uiState.extractedText) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                ExtractedTextCard(
                    text = uiState.inputText,
                    onTextChanged = { viewModel.onInputTextChanged(it) },
                    onLearnClick = { onNavigateToLearn(uiState.inputText) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0B0D)
@Composable
private fun ScanScreenPreview() {
    SnapTutorTheme {
        // Preview
    }
}
