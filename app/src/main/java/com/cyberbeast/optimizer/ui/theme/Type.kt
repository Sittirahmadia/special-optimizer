package com.cyberbeast.optimizer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Tech/Monospace Font Family (using system monospace as fallback)
val CyberFontFamily = FontFamily.Monospace

val CyberTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        letterSpacing = 2.sp,
        color = CyberTextPrimary
    ),
    displayMedium = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 1.5.sp,
        color = CyberTextPrimary
    ),
    displaySmall = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 1.sp,
        color = CyberTextPrimary
    ),
    headlineLarge = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp,
        color = CyberTextPrimary
    ),
    headlineMedium = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
        color = CyberTextPrimary
    ),
    headlineSmall = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp,
        color = CyberTextPrimary
    ),
    titleLarge = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = CyberTextPrimary
    ),
    titleMedium = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp,
        color = CyberTextPrimary
    ),
    titleSmall = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        color = CyberTextSecondary
    ),
    bodyLarge = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp,
        color = CyberTextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp,
        color = CyberTextSecondary
    ),
    bodySmall = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.25.sp,
        color = CyberTextMuted
    ),
    labelLarge = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        color = NeonCyan
    ),
    labelMedium = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = NeonCyan
    ),
    labelSmall = TextStyle(
        fontFamily = CyberFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 8.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        color = CyberTextMuted
    )
)
