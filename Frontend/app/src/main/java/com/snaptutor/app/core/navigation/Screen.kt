package com.snaptutor.app.core.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes using kotlinx.serialization.
 * Each screen is a serializable object/class that defines its route.
 */
sealed interface Screen {

    @Serializable
    data object Dashboard : Screen

    @Serializable
    data object Scan : Screen

    @Serializable
    data class Learn(val questionText: String) : Screen

    @Serializable
    data class Evaluation(val topic: String) : Screen

    @Serializable
    data object Progress : Screen
}
