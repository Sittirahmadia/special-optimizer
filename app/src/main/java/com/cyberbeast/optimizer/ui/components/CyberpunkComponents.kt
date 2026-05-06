package com.cyberbeast.optimizer.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberbeast.optimizer.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun CyberCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .border(1.dp, CyberCardBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CyberCardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun NeonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = NeonCyan,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color.copy(alpha = 0.2f),
            contentColor = color,
            disabledContainerColor = CyberTextMuted.copy(alpha = 0.2f),
            disabledContentColor = CyberTextMuted
        ),
        border = BorderStroke(1.dp, if (enabled) color else CyberTextMuted)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = if (enabled) color else CyberTextMuted)
    }
}

@Composable
fun CyberToggle(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isChecked by remember { mutableStateOf(checked) }

    CyberCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = CyberTextPrimary)
                Text(description, style = MaterialTheme.typography.bodySmall, color = CyberTextSecondary)
            }
            Switch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onCheckedChange(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = NeonCyan,
                    checkedTrackColor = NeonCyan.copy(alpha = 0.3f),
                    uncheckedThumbColor = CyberTextSecondary,
                    uncheckedTrackColor = CyberCardBorder
                )
            )
        }
    }
}

@Composable
fun NeonProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = NeonCyan
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(CyberCardBorder, RoundedCornerShape(4.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(
                    brush = Brush.horizontalGradient(listOf(color, color.copy(alpha = 0.7f))),
                    shape = RoundedCornerShape(4.dp)
                )
                .shadow(4.dp, RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun GlitchText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = NeonCyan
) {
    var glitchOffset by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            glitchOffset = if ((0..10).random() > 8) (1..5).random().toFloat() else 0f
            delay((50..300).random().toLong())
        }
    }

    Box(modifier = modifier) {
        Text(
            text = text,
            color = color.copy(alpha = 0.7f),
            modifier = Modifier.offset(x = glitchOffset.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = text,
            color = NeonMagenta.copy(alpha = 0.7f),
            modifier = Modifier.offset(x = -glitchOffset.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun HolographicCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "hologram")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        CyberCardBg,
                        CyberCardBg.copy(alpha = 0.9f),
                        NeonCyan.copy(alpha = 0.05f),
                        CyberCardBg.copy(alpha = 0.9f),
                        CyberCardBg
                    ),
                    start = Offset(shimmerOffset - 500f, 0f),
                    end = Offset(shimmerOffset, 0f)
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, CyberCardBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun ParticleBackground(modifier: Modifier = Modifier) {
    val particles = remember { List(50) { Particle.random() } }
    val time by rememberInfiniteTransition(label = "particles").animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(100000, easing = LinearEasing)),
        label = "time"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val x = (particle.x + time * particle.speedX) % size.width
            val y = (particle.y + time * particle.speedY) % size.height
            drawCircle(
                color = particle.color.copy(alpha = particle.alpha),
                radius = particle.size,
                center = Offset(x, y)
            )
        }
    }
}

data class Particle(
    val x: Float,
    val y: Float,
    val speedX: Float,
    val speedY: Float,
    val size: Float,
    val color: Color,
    val alpha: Float
) {
    companion object {
        fun random() = Particle(
            x = (0..1000).random().toFloat(),
            y = (0..2000).random().toFloat(),
            speedX = (-1..1).random().toFloat() * 0.5f,
            speedY = (-2..-0).random().toFloat() * 0.5f,
            size = (1..4).random().toFloat(),
            color = listOf(NeonCyan, NeonMagenta, NeonPurple, NeonBlue).random(),
            alpha = (0.1f..0.5f).random()
        )
    }
}

private fun ClosedFloatingPointRange<Float>.random(): Float =
    start + (endInclusive - start) * kotlin.random.Random.nextFloat()
