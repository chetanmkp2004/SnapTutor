package com.snaptutor.app.evaluation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.snaptutor.app.core.ui.components.PrimaryButton
import com.snaptutor.app.core.ui.theme.SnapTutorTheme
import com.snaptutor.app.evaluation.components.AnswerOption
import com.snaptutor.app.evaluation.components.FeedbackCard
import com.snaptutor.app.evaluation.components.MasteryCard
import com.snaptutor.app.evaluation.components.QuizProgress
import com.snaptutor.app.evaluation.components.QuizQuestionCard
import com.snaptutor.app.evaluation.components.ResultCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluationScreen(
    topic: String,
    viewModel: EvaluationViewModel,
    onNavigateToDashboard: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(topic) {
        viewModel.loadQuiz(topic)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Practice Quiz",
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
                    message = "Loading quiz questions..."
                )
            }

            is UiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.retakeQuiz() },
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
                    if (!data.isSubmitted) {
                        val currentQ = data.quiz.questions.getOrNull(data.currentQuestionIndex)
                        if (currentQ != null) {
                            QuizProgress(
                                currentIndex = data.currentQuestionIndex,
                                totalCount = data.quiz.questions.size,
                                difficulty = data.quiz.difficulty,
                                modifier = Modifier.fillMaxWidth()
                            )

                            QuizQuestionCard(
                                questionText = currentQ.questionText,
                                modifier = Modifier.fillMaxWidth()
                            )

                            val selectedOption = data.selectedAnswers[data.currentQuestionIndex]

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                currentQ.options.forEachIndexed { optIndex, optText ->
                                    AnswerOption(
                                        optionIndex = optIndex,
                                        optionText = optText,
                                        isSelected = selectedOption == optIndex,
                                        isSubmitted = false,
                                        isCorrectAnswer = false,
                                        onSelect = {
                                            viewModel.selectOption(data.currentQuestionIndex, optIndex)
                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                if (data.currentQuestionIndex > 0) {
                                    OutlinedButton(
                                        onClick = { viewModel.previousQuestion() },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Previous")
                                    }
                                }

                                if (data.currentQuestionIndex < data.quiz.questions.size - 1) {
                                    Button(
                                        onClick = { viewModel.nextQuestion() },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text("Next")
                                    }
                                } else {
                                    Button(
                                        onClick = { viewModel.submitQuiz() },
                                        modifier = Modifier.weight(1f),
                                        enabled = data.selectedAnswers.isNotEmpty(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text("Submit Quiz")
                                    }
                                }
                            }
                        }
                    } else {
                        // Submitted results view
                        data.result?.let { result ->
                            ResultCard(result = result, modifier = Modifier.fillMaxWidth())
                        }

                        data.adaptationState?.let { adaptation ->
                            MasteryCard(adaptation = adaptation, modifier = Modifier.fillMaxWidth())
                        }

                        FeedbackCard(
                            quiz = data.quiz,
                            selectedAnswers = data.selectedAnswers,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.retakeQuiz() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Retake")
                            }

                            PrimaryButton(
                                text = "Back to Dashboard",
                                onClick = onNavigateToDashboard,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0F)
@Composable
private fun EvaluationScreenPreview() {
    SnapTutorTheme {
        // Preview
    }
}
