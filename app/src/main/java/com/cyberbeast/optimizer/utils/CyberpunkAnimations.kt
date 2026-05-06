package com.cyberbeast.optimizer.utils

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.cyberbeast.optimizer.ui.theme.NeonCyan

@Composable
fun rememberPulseAnimation(): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    return infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    ).value
}
