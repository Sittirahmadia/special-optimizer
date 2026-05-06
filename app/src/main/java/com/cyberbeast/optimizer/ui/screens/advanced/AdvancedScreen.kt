package com.cyberbeast.optimizer.ui.screens.advanced

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyberbeast.optimizer.ui.components.*
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun AdvancedScreen() {
    Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("ADVANCED PANEL", style = MaterialTheme.typography.headlineMedium, color = NeonCyan)
            Text("Manual controls & commands", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)

            Spacer(modifier = Modifier.height(16.dp))

            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("MANUAL SETEDIT", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Table (global/system/secure)", color = CyberTextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonCyan, unfocusedBorderColor = CyberCardBorder)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Key", color = CyberTextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonCyan, unfocusedBorderColor = CyberCardBorder)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Value", color = CyberTextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonCyan, unfocusedBorderColor = CyberCardBorder)
                )
                Spacer(modifier = Modifier.height(8.dp))
                NeonButton("EXECUTE", {}, Modifier.fillMaxWidth(), NeonCyan)
            }

            Spacer(modifier = Modifier.height(16.dp))

            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("COMMAND EXECUTOR", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Shell command", color = CyberTextSecondary) },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonCyan, unfocusedBorderColor = CyberCardBorder)
                )
                Spacer(modifier = Modifier.height(8.dp))
                NeonButton("RUN", {}, Modifier.fillMaxWidth(), NeonMagenta)
            }

            Spacer(modifier = Modifier.height(16.dp))

            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("BACKUP & RESTORE", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    NeonButton("EXPORT", {}, Modifier.weight(1f), NeonBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    NeonButton("IMPORT", {}, Modifier.weight(1f), NeonPurple)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
