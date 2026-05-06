package com.cyberbeast.optimizer.ui.screens.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyberbeast.optimizer.ui.components.*
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun ShizukuSetupScreen() {
    Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            GlitchText("SHIZUKU SETUP", color = NeonCyan)

            Text(
                "Required for system-level optimization",
                style = MaterialTheme.typography.bodyMedium,
                color = CyberTextSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("STEP 1: INSTALL SHIZUKU", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Install Shizuku app from Play Store", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("STEP 2: WIRELESS DEBUGGING", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                Text("1. Enable Developer Options\n2. Go to Wireless Debugging\n3. Pair with pairing code\n4. Start Shizuku", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("STEP 3: GRANT PERMISSION", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Allow this app to use Shizuku", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            NeonButton("CHECK STATUS", {}, Modifier.fillMaxWidth(), NeonGreen)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
