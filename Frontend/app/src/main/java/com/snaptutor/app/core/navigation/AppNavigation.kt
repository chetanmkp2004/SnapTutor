package com.snaptutor.app.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Insights
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.snaptutor.app.core.di.AppContainer
import com.snaptutor.app.dashboard.DashboardScreen
import com.snaptutor.app.dashboard.DashboardViewModel
import com.snaptutor.app.evaluation.EvaluationScreen
import com.snaptutor.app.evaluation.EvaluationViewModel
import com.snaptutor.app.learn.LearnScreen
import com.snaptutor.app.learn.LearnViewModel
import com.snaptutor.app.progress.ProgressScreen
import com.snaptutor.app.scan.ScanScreen
import com.snaptutor.app.scan.ScanViewModel

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.snaptutor.app.core.ui.theme.StBackground
import com.snaptutor.app.core.ui.theme.StLensPrimary
import com.snaptutor.app.core.ui.theme.StSurfaceBorder
import com.snaptutor.app.core.ui.theme.StSurfaceVariant
import com.snaptutor.app.core.ui.theme.StTextPrimary
import com.snaptutor.app.core.ui.theme.StTextSecondary

@Composable
fun AppNavigation(
    container: AppContainer,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Show bottom bar on top-level screens
    val isTopLevel = currentRoute?.contains("Dashboard") == true ||
            currentRoute?.contains("Progress") == true

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = StBackground,
        bottomBar = {
            if (isTopLevel) {
                NavigationBar(
                    containerColor = StSurfaceVariant,
                    contentColor = StTextPrimary
                ) {
                    NavigationBarItem(
                        selected = currentRoute.contains("Dashboard"),
                        onClick = {
                            navController.navigate(Screen.Dashboard) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.Home,
                                contentDescription = "Dashboard"
                            )
                        },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = StLensPrimary,
                            selectedTextColor = StLensPrimary,
                            unselectedIconColor = StTextSecondary,
                            unselectedTextColor = StTextSecondary,
                            indicatorColor = StLensPrimary.copy(alpha = 0.18f)
                        )
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.Scan)
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.CameraAlt,
                                contentDescription = "Scan"
                            )
                        },
                        label = { Text("Scan") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = StLensPrimary,
                            selectedTextColor = StLensPrimary,
                            unselectedIconColor = StTextSecondary,
                            unselectedTextColor = StTextSecondary,
                            indicatorColor = StLensPrimary.copy(alpha = 0.18f)
                        )
                    )

                    NavigationBarItem(
                        selected = currentRoute.contains("Progress"),
                        onClick = {
                            navController.navigate(Screen.Progress) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.Insights,
                                contentDescription = "Progress"
                            )
                        },
                        label = { Text("Progress") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = StLensPrimary,
                            selectedTextColor = StLensPrimary,
                            unselectedIconColor = StTextSecondary,
                            unselectedTextColor = StTextSecondary,
                            indicatorColor = StLensPrimary.copy(alpha = 0.18f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> (fullWidth * 0.15f).toInt() },
                    animationSpec = tween(280, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(280))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -(fullWidth * 0.15f).toInt() },
                    animationSpec = tween(280, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(200))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -(fullWidth * 0.15f).toInt() },
                    animationSpec = tween(280, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(280))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> (fullWidth * 0.15f).toInt() },
                    animationSpec = tween(280, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(200))
            }
        ) {
            composable<Screen.Dashboard> {
                val viewModel = remember {
                    DashboardViewModel(
                        progressRepository = container.progressRepository,
                        syncManager = container.syncManager
                    )
                }
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToScan = {
                        navController.navigate(Screen.Scan)
                    },
                    onNavigateToLearn = { questionText ->
                        navController.navigate(Screen.Learn(questionText))
                    }
                )
            }

            composable<Screen.Scan> {
                val viewModel = remember {
                    ScanViewModel(ocrProcessor = container.ocrProcessor)
                }
                ScanScreen(
                    viewModel = viewModel,
                    onNavigateToLearn = { questionText ->
                        navController.navigate(Screen.Learn(questionText))
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<Screen.Learn> { backStackEntry ->
                val learnRoute = backStackEntry.toRoute<Screen.Learn>()
                val viewModel = remember {
                    LearnViewModel(learningRepository = container.learningRepository)
                }
                LearnScreen(
                    questionText = learnRoute.questionText,
                    viewModel = viewModel,
                    onNavigateToEvaluation = { topic ->
                        navController.navigate(Screen.Evaluation(topic))
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<Screen.Evaluation> { backStackEntry ->
                val evalRoute = backStackEntry.toRoute<Screen.Evaluation>()
                val viewModel = remember {
                    EvaluationViewModel(
                        evaluationRepository = container.evaluationRepository,
                        progressRepository = container.progressRepository
                    )
                }
                EvaluationScreen(
                    topic = evalRoute.topic,
                    viewModel = viewModel,
                    onNavigateToDashboard = {
                        navController.navigate(Screen.Dashboard) {
                            popUpTo(Screen.Dashboard) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<Screen.Progress> {
                ProgressScreen(
                    progressRepository = container.progressRepository,
                    onNavigateToLearn = { topic ->
                        navController.navigate(Screen.Learn(topic))
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
