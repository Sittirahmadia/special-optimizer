package com.cyberbeast.optimizer.ui.screens.tweaks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberbeast.optimizer.ui.components.*
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun TweaksScreen(
    viewModel: TweaksViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val lastResult by viewModel.lastResult.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("SYSTEM TWEAKS", style = MaterialTheme.typography.headlineMedium, color = NeonCyan)
            Text("Optimize your Redmi 14C", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)

            Spacer(modifier = Modifier.height(16.dp))

            // Refresh Rate
            SectionTitle("REFRESH RATE")
            Row(modifier = Modifier.fillMaxWidth()) {
                NeonButton("60Hz", { viewModel.setRefreshRate("60") }, Modifier.weight(1f), NeonBlue)
                NeonButton("90Hz", { viewModel.setRefreshRate("90") }, Modifier.weight(1f), NeonCyan)
                NeonButton("120Hz", { viewModel.setRefreshRate("120") }, Modifier.weight(1f), NeonGreen)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Animation
            SectionTitle("ANIMATION SCALE")
            Row(modifier = Modifier.fillMaxWidth()) {
                NeonButton("OFF", { viewModel.setAnimationScale("0.0") }, Modifier.weight(1f), CyberDanger)
                NeonButton("0.25x", { viewModel.setAnimationScale("0.25") }, Modifier.weight(1f), NeonOrange)
                NeonButton("0.5x", { viewModel.setAnimationScale("0.5") }, Modifier.weight(1f), NeonYellow)
                NeonButton("1.0x", { viewModel.setAnimationScale("1.0") }, Modifier.weight(1f), CyberSuccess)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Toggles
            SectionTitle("PERFORMANCE TOGGLES")
            CyberToggle("GPU Rendering", "Force GPU rendering for all drawing", false, onCheckedChange = { viewModel.toggleGpuRendering(it) })
            CyberToggle("Performance Mode", "Maximize CPU/GPU performance", false, onCheckedChange = { viewModel.togglePerformanceMode(it) })
            CyberToggle("Disable Power Keeper", "Stop MIUI power management", false, onCheckedChange = { viewModel.togglePowerKeeper(it) })
            CyberToggle("Aggressive RAM", "Kill background apps aggressively", false, onCheckedChange = { viewModel.toggleRamManager(it) })
            CyberToggle("Touch Boost", "Reduce touch latency", false, onCheckedChange = { viewModel.optimizeTouch(it) })
            CyberToggle("Network Optimize", "Optimize TCP/DNS settings", false, onCheckedChange = { viewModel.optimizeNetwork(it) })
            CyberToggle("Thermal Reduce", "Reduce thermal throttling (RISKY)", false, onCheckedChange = { viewModel.reduceThermal(it) })
            CyberToggle("Disable Ads", "Remove MIUI ads & analytics", false, onCheckedChange = { viewModel.toggleAds(it) })
            CyberToggle("Force MSAA", "Enable 4x MSAA anti-aliasing", false, onCheckedChange = { viewModel.forceMsaa(it) })
            CyberToggle("Disable Blur", "Remove blur effects for FPS", false, onCheckedChange = { viewModel.disableBlur(it) })

            Spacer(modifier = Modifier.height(16.dp))

            // Renderer
            SectionTitle("RENDERER PIPELINE")
            Row(modifier = Modifier.fillMaxWidth()) {
                NeonButton("OpenGL", { viewModel.setRenderer("opengl") }, Modifier.weight(1f), NeonBlue)
                NeonButton("Vulkan", { viewModel.setRenderer("vulkan") }, Modifier.weight(1f), NeonPurple)
                NeonButton("SkiaGL", { viewModel.setRenderer("skiagl") }, Modifier.weight(1f), NeonCyan)
                NeonButton("SkiaVK", { viewModel.setRenderer("skiavk") }, Modifier.weight(1f), NeonMagenta)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CPU Governor
            SectionTitle("CPU GOVERNOR")
            Row(modifier = Modifier.fillMaxWidth()) {
                NeonButton("Performance", { viewModel.setCpuGovernor("performance") }, Modifier.weight(1f), NeonRed)
                NeonButton("Schedutil", { viewModel.setCpuGovernor("schedutil") }, Modifier.weight(1f), NeonYellow)
                NeonButton("Interactive", { viewModel.setCpuGovernor("interactive") }, Modifier.weight(1f), NeonOrange)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // I/O Scheduler
            SectionTitle("I/O SCHEDULER")
            Row(modifier = Modifier.fillMaxWidth()) {
                NeonButton("Noop", { viewModel.setIoScheduler("noop") }, Modifier.weight(1f), CyberTextSecondary)
                NeonButton("Deadline", { viewModel.setIoScheduler("deadline") }, Modifier.weight(1f), NeonCyan)
                NeonButton("BFQ", { viewModel.setIoScheduler("bfq") }, Modifier.weight(1f), NeonBlue)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Result
            lastResult?.let {
                CyberCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        if (it.success) "SUCCESS: ${it.stdout}" else "FAILED: ${it.stderr}",
                        color = if (it.success) CyberSuccess else CyberDanger,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = NeonCyan)
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
        color = NeonCyan,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
