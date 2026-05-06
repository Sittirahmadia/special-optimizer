package com.cyberbeast.optimizer.ui.theme

import androidx.compose.ui.graphics.Color

// Core Cyberpunk Palette
val CyberBlack = Color(0xFF0A0A0F)
val CyberDark = Color(0xFF12121A)
val CyberDarkElevated = Color(0xFF1A1A28)
val CyberCardBg = Color(0xFF151520)
val CyberCardBorder = Color(0xFF2A2A3E)

// Neon Colors
val NeonCyan = Color(0xFF00F0FF)
val NeonMagenta = Color(0xFFFF00AA)
val NeonPurple = Color(0xFFB829DD)
val NeonBlue = Color(0xFF2979FF)
val NeonGreen = Color(0xFF00E676)
val NeonPink = Color(0xFFFF4081)
val NeonYellow = Color(0xFFFFD740)
val NeonOrange = Color(0xFFFF6D00)
val NeonRed = Color(0xFFFF1744)

// Glow Effects (with alpha)
val GlowCyan = Color(0xFF00F0FF)
val GlowMagenta = Color(0xFFFF00AA)
val GlowPurple = Color(0xFFB829DD)

// Text Colors
val CyberTextPrimary = Color(0xFFE0E0E0)
val CyberTextSecondary = Color(0xFF8A8A9A)
val CyberTextMuted = Color(0xFF5A5A6A)

// Status Colors
val CyberSuccess = Color(0xFF00E676)
val CyberWarning = Color(0xFFFF9100)
val CyberDanger = Color(0xFFFF1744)
val CyberInfo = Color(0xFF2979FF)

// Grid Background
val GridLineColor = Color(0xFF1A1A2E)
val GridLineBright = Color(0xFF2A2A40)

// Gradient Stops
val GradientCyanPurple = listOf(NeonCyan, NeonPurple, NeonMagenta)
val GradientBlueGreen = listOf(NeonBlue, NeonGreen)
val GradientMagentaPink = listOf(NeonMagenta, NeonPink)
val GradientDanger = listOf(NeonRed, NeonOrange)

// Holographic shimmer
val HolographicColors = listOf(
    NeonCyan.copy(alpha = 0.3f),
    NeonMagenta.copy(alpha = 0.2f),
    NeonPurple.copy(alpha = 0.3f),
    NeonBlue.copy(alpha = 0.2f)
)
