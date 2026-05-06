package com.cyberbeast.optimizer.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberbeast.optimizer.ui.components.*
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val stats by viewModel.systemStats.collectAsState()
    val shizukuStatus by viewModel.shizukuStatus.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val activeProfile by viewModel.activeProfile.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
        ParticleBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            GlitchText(
                text = "CYBER BEAST",
                modifier = Modifier.padding(top = 32.dp, bottom = 8.dp),
                color = NeonCyan
            )
            Text(
                "REDMI 14C OPTIMIZER",
                style = MaterialTheme.typography.titleSmall,
                color = NeonMagenta,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Shizuku Status
            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (shizukuStatus) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (shizukuStatus) CyberSuccess else CyberWarning,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Shizuku Status", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)
                        Text(
                            if (shizukuStatus) "CONNECTED" else "DISCONNECTED",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (shizukuStatus) CyberSuccess else CyberWarning
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // System Stats Grid
            Row(modifier = Modifier.fillMaxWidth()) {
                StatCard("CPU", "${stats.cpuUsage.toInt()}%", NeonCyan, Modifier.weight(1f))
                StatCard("RAM", "${stats.ramUsagePercent.toInt()}%", NeonMagenta, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                StatCard("TEMP", "${stats.batteryTemp.toInt()}°C", NeonOrange, Modifier.weight(1f))
                StatCard("FPS", "${stats.fps}", NeonGreen, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Active Profile
            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("ACTIVE PROFILE", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Text(activeProfile, style = MaterialTheme.typography.headlineMedium, color = CyberTextPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Action Buttons
            NeonButton(
                text = if (isLoading) "PROCESSING..." else "ONE TAP CYBER BOOST",
                onClick = { viewModel.activateCyberBeastMode() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                color = NeonCyan
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                NeonButton(
                    text = "RESET ALL",
                    onClick = { viewModel.resetAll() },
                    modifier = Modifier.weight(1f),
                    color = CyberWarning
                )
                Spacer(modifier = Modifier.width(8.dp))
                NeonButton(
                    text = "REBOOT",
                    onClick = { viewModel.reboot() },
                    modifier = Modifier.weight(1f),
                    color = CyberDanger
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    CyberCard(modifier = modifier.padding(4.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = CyberTextSecondary)
            Text(value, style = MaterialTheme.typography.headlineMedium, color = color)
        }
    }
}
