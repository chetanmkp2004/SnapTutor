package com.snaptutor.app.core.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * SnapTutor color palette — premium, dark-first design.
 * All colors centralized here; no ad-hoc color values in feature code.
 */

// ── Surface & Depth Hierarchy (Tone-driven, no shadow blur required) ──
val StBackground = Color(0xFF0A0B0D)        // Base optical dark chamber
val StSurface = Color(0xFF15171B)           // Level 1: Raised structural surfaces & hero cards
val StSurfaceVariant = Color(0xFF1D2024)    // Level 2: Elevated interactive action rows
val StSurfaceElevated = Color(0xFF24282F)   // Level 3: Popups, active pills, high-contrast controls
val StSurfaceBorder = Color(0xFF262A30)     // Hairline reticle border (1dp precision lines)
val StDivider = Color(0xFF1E2228)           // Subdued metric delimiter

// ── Primary AI Focal Accent (Restrained: camera capture, active AI inference) ──
val StLensPrimary = Color(0xFF6366F1)       // Electric focal indigo
val StLensPrimaryLight = Color(0xFF818CF8)  // Highlight edge
val StLensPrimaryDark = Color(0xFF4338CA)   // Deep focus fill
val StFocusLock = Color(0xFF38BDF8)         // Optical reticle focus-locked cyan

// ── Secondary Telemetry & Mastery Accents (Progress, stats, streaks) ──
val StProgressTeal = Color(0xFF2DD4BF)      // Desaturated precision teal for mastery ring & completion
val StCalibratedAmber = Color(0xFFE5A93C)   // Warm optical amber for streaks and calibration

// ── Text & Telemetry Typography ──
val StTextPrimary = Color(0xFFF3F4F6)       // Ultra-clean high contrast readout
val StTextSecondary = Color(0xFF9CA3AF)     // Technical metadata labels
val StTextMuted = Color(0xFF6B7280)         // Subtle caption & guide marks
val StOnPrimary = Color(0xFFFFFFFF)

// ── Semantic & Status Indicators ──
val StSuccess = Color(0xFF2DD4BF)
val StSuccessDark = Color(0xFF0F766E)
val StWarning = StCalibratedAmber
val StError = Color(0xFFEF4444)
val StErrorDark = Color(0xFFB91C1C)

val StOnlineIndicator = StProgressTeal
val StOfflineIndicator = StCalibratedAmber
val StSyncingIndicator = StLensPrimary

// ── Backwards-compatible Aliases ──
val StPurple = StLensPrimary
val StPurpleLight = StLensPrimaryLight
val StPurpleDark = StLensPrimaryDark
val StCyan = StFocusLock
val StCyanDark = Color(0xFF0284C7)
val StOnBackground = StTextPrimary
val StOnSurface = StTextPrimary
val StOnSurfaceVariant = StTextSecondary
val StCardBackground = StSurface
val StCardBorder = StSurfaceBorder
