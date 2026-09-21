package com.snaptutor.app.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snaptutor.app.core.state.UiState
import com.snaptutor.app.core.ui.components.ErrorView
import com.snaptutor.app.core.ui.components.LoadingView
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.learn.components.ConceptCard
import com.snaptutor.app.learn.components.ExplanationCard
import com.snaptutor.app.learn.components.PracticeButton
import com.snaptutor.app.learn.components.SourceCard
import com.snaptutor.app.learn.components.StepByStepCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    questionText: String,
    viewModel: LearnViewModel,
    onNavigateToEvaluation: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(questionText) {
        viewModel.loadExplanation(questionText)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Learn & Understand",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is UiState.Loading -> {
                LoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    message = "Analyzing question & generating step-by-step guidance..."
                )
            }

            is UiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.retry() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is UiState.Success -> {
                val data = state.data
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ExplanationCard(
                        questionText = data.questionText,
                        explanation = data.response.explanation,
                        answer = data.response.answer,
                        isOnDevice = data.response.isOnDevice,
                        modifier = Modifier.fillMaxWidth()
                    )

                    StepByStepCard(
                        steps = data.response.steps,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (data.response.keyConcepts.isNotEmpty()) {
                        ConceptCard(
                            concepts = data.response.keyConcepts,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (data.response.sources.isNotEmpty()) {
                        SourceCard(
                            sources = data.response.sources,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val topic = data.response.keyConcepts.firstOrNull() ?: "Linear Equations"
                    PracticeButton(
                        onClick = { onNavigateToEvaluation(topic) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0F)
@Composable
private fun LearnScreenPreview() {
    SnapTutorTheme {
        // Preview
    }
}
