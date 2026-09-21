package com.snaptutor.app.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = StLensPrimary,
    onPrimary = StOnPrimary,
    primaryContainer = StLensPrimaryDark,
    secondary = StProgressTeal,
    onSecondary = StBackground,
    secondaryContainer = StSuccessDark,
    tertiary = StCalibratedAmber,
    onTertiary = StBackground,
    background = StBackground,
    onBackground = StTextPrimary,
    surface = StSurface,
    onSurface = StTextPrimary,
    surfaceVariant = StSurfaceVariant,
    onSurfaceVariant = StTextSecondary,
    error = StError,
    onError = StOnPrimary,
    errorContainer = StErrorDark,
    outline = StSurfaceBorder,
    outlineVariant = StDivider,
    surfaceContainerLowest = StBackground,
    surfaceContainerLow = StSurface,
    surfaceContainer = StSurfaceVariant,
    surfaceContainerHigh = StSurfaceElevated,
    surfaceContainerHighest = StSurfaceElevated
)

/**
 * SnapTutor Material 3 dark-first theme.
 * No light theme variant for this phase — dark-first per design direction.
 */
@Composable
fun SnapTutorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = SnapTutorTypography,
        content = content
    )
}
