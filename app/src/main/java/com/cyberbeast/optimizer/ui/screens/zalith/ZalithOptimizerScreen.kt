package com.cyberbeast.optimizer.ui.screens.zalith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberbeast.optimizer.ui.components.*
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun ZalithOptimizerScreen(
    viewModel: ZalithViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val lastResult by viewModel.lastResult.collectAsState()
    val zalithStatus by viewModel.zalithStatus.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            GlitchText("ZALITH LAUNCHER", color = NeonGreen)
            Text(
                "MINECRAFT JAVA OPTIMIZER",
                style = MaterialTheme.typography.titleSmall,
                color = NeonCyan,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Status Card
            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (zalithStatus) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (zalithStatus) CyberSuccess else CyberWarning,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Zalith Status", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)
                        Text(
                            if (zalithStatus) "INSTALLED & READY" else "NOT INSTALLED",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (zalithStatus) CyberSuccess else CyberWarning
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Boost
            NeonButton(
                text = if (isLoading) "OPTIMIZING..." else "ZALITH MAX BOOST",
                onClick = { viewModel.applyZalithMaxBoost() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                color = NeonGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "One-tap optimization for Minecraft Java via Zalith Launcher",
                style = MaterialTheme.typography.bodySmall,
                color = CyberTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Renderer Section
            SectionTitle("RENDERER PIPELINE")
            Text("Select best renderer for your GPU", style = MaterialTheme.typography.bodySmall, color = CyberTextSecondary)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                NeonButton("GL4ES", { viewModel.setRenderer("gl4es") }, Modifier.weight(1f), NeonCyan)
                NeonButton("ANGLE", { viewModel.setRenderer("angle") }, Modifier.weight(1f), NeonBlue)
                NeonButton("VIRGL", { viewModel.setRenderer("virgl") }, Modifier.weight(1f), NeonPurple)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                NeonButton("ZINK", { viewModel.setRenderer("zink") }, Modifier.weight(1f), NeonMagenta)
                NeonButton("LTW", { viewModel.setRenderer("ltw") }, Modifier.weight(1f), NeonYellow)
                NeonButton("MOBILE GLUES", { viewModel.setRenderer("mobileglues") }, Modifier.weight(1f), NeonGreen)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "• GL4ES: Best for Mali/Adreno (default)
• ANGLE: Direct3D backend
• VIRGL: Virtual GPU (cloud gaming)
• ZINK: Vulkan-based OpenGL
• LTW: Lightweight wrapper
• MobileGlues: Best for modern GPUs",
                style = MaterialTheme.typography.bodySmall,
                color = CyberTextMuted
            )

            Spacer(modifier = Modifier.height(24.dp))

            // JVM Memory Section
            SectionTitle("JVM MEMORY ALLOCATION")
            Text("RAM allocation for Minecraft Java", style = MaterialTheme.typography.bodySmall, color = CyberTextSecondary)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                NeonButton("2GB", { viewModel.setJvmMemory("2G") }, Modifier.weight(1f), NeonBlue)
                NeonButton("4GB", { viewModel.setJvmMemory("4G") }, Modifier.weight(1f), NeonCyan)
                NeonButton("6GB", { viewModel.setJvmMemory("6G") }, Modifier.weight(1f), NeonGreen)
                NeonButton("8GB", { viewModel.setJvmMemory("8G") }, Modifier.weight(1f), NeonMagenta)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // JVM Args Presets
            SectionTitle("JVM ARGUMENTS PRESETS")
            CyberToggle(
                title = "G1GC Optimized",
                description = "Use G1 Garbage Collector for better performance",
                checked = false,
                onCheckedChange = { viewModel.toggleG1GC(it) }
            )
            CyberToggle(
                title = "Always PreTouch",
                description = "Pre-touch memory pages to reduce lag spikes",
                checked = false,
                onCheckedChange = { viewModel.togglePreTouch(it) }
            )
            CyberToggle(
                title = "Disable Explicit GC",
                description = "Prevent mods from calling System.gc()",
                checked = false,
                onCheckedChange = { viewModel.toggleDisableExplicitGC(it) }
            )
            CyberToggle(
                title = "Large Pages",
                description = "Enable large memory pages (if supported)",
                checked = false,
                onCheckedChange = { viewModel.toggleLargePages(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Performance Tweaks
            SectionTitle("ZALITH PERFORMANCE TWEAKS")

            CyberToggle(
                title = "Sustainable Performance",
                description = "Enable sustainable performance mode for stable FPS",
                checked = false,
                onCheckedChange = { viewModel.toggleSustainablePerf(it) }
            )
            CyberToggle(
                title = "Force High Performance Cores",
                description = "Use big cores only (Redmi 14C Helio G81)",
                checked = false,
                onCheckedChange = { viewModel.toggleHighPerfCores(it) }
            )
            CyberToggle(
                title = "Reduce Resolution",
                description = "Lower render resolution for higher FPS",
                checked = false,
                onCheckedChange = { viewModel.toggleReduceResolution(it) }
            )
            CyberToggle(
                title = "Fast Mode",
                description = "Disable fancy graphics, enable fast mode",
                checked = false,
                onCheckedChange = { viewModel.toggleFastMode(it) }
            )
            CyberToggle(
                title = "Disable VSync",
                description = "Remove frame rate cap for uncapped FPS",
                checked = false,
                onCheckedChange = { viewModel.toggleVSync(it) }
            )
            CyberToggle(
                title = "OptiFine Compatible",
                description = "Settings optimized for OptiFine/OptiMobile",
                checked = false,
                onCheckedChange = { viewModel.toggleOptiFineCompat(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Advanced Settings
            SectionTitle("ADVANCED ZALITH SETTINGS")

            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("CUSTOM JVM ARGS", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Enter custom JVM arguments...", color = CyberTextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = CyberCardBorder
                    ),
                    minLines = 3
                )
                Spacer(modifier = Modifier.height(8.dp))
                NeonButton("APPLY JVM ARGS", {}, Modifier.fillMaxWidth(), NeonGreen)
            }

            Spacer(modifier = Modifier.height(12.dp))

            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("GAME DIRECTORY", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "/sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft",
                    style = MaterialTheme.typography.bodySmall,
                    color = NeonBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    NeonButton("CLEAR CACHE", {}, Modifier.weight(1f), CyberWarning)
                    NeonButton("RESET ZALITH", {}, Modifier.weight(1f), CyberDanger)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Result
            lastResult?.let {
                CyberCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        if (it.success) "✓ ${it.stdout}" else "✗ ${it.stderr}",
                        color = if (it.success) CyberSuccess else CyberDanger,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = NeonGreen)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge,
        color = NeonGreen,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
