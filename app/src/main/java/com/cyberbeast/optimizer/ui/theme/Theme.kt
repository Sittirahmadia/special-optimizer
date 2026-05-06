package com.cyberbeast.optimizer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val CyberDarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = CyberBlack,
    primaryContainer = CyberDarkElevated,
    onPrimaryContainer = NeonCyan,
    secondary = NeonMagenta,
    onSecondary = CyberBlack,
    secondaryContainer = CyberCardBg,
    onSecondaryContainer = NeonMagenta,
    tertiary = NeonPurple,
    onTertiary = CyberBlack,
    tertiaryContainer = CyberDarkElevated,
    onTertiaryContainer = NeonPurple,
    background = CyberBlack,
    onBackground = CyberTextPrimary,
    surface = CyberDark,
    onSurface = CyberTextPrimary,
    surfaceVariant = CyberCardBg,
    onSurfaceVariant = CyberTextSecondary,
    surfaceTint = NeonCyan.copy(alpha = 0.1f),
    inverseSurface = CyberTextPrimary,
    inverseOnSurface = CyberBlack,
    error = CyberDanger,
    onError = CyberBlack,
    errorContainer = CyberDanger.copy(alpha = 0.2f),
    onErrorContainer = CyberDanger,
    outline = CyberCardBorder,
    outlineVariant = GridLineColor,
    scrim = CyberBlack.copy(alpha = 0.8f)
)

@Composable
fun CyberBeastTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> CyberDarkColorScheme
        else -> CyberDarkColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = CyberBlack.toArgb()
            window.navigationBarColor = CyberBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CyberTypography,
        shapes = CyberShapes,
        content = content
    )
}
