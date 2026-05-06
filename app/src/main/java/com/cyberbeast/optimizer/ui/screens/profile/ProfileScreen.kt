package com.cyberbeast.optimizer.ui.screens.profile

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberbeast.optimizer.ui.components.*
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profiles by viewModel.profiles.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("PROFILE MANAGER", style = MaterialTheme.typography.headlineMedium, color = NeonCyan)
            Text("Save and load configurations", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)

            Spacer(modifier = Modifier.height(16.dp))

            // Create New
            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("SAVE CURRENT", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Profile name", color = CyberTextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonCyan)
                )
                Spacer(modifier = Modifier.height(8.dp))
                NeonButton("SAVE", {}, Modifier.fillMaxWidth(), NeonGreen)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Saved Profiles
            Text("SAVED PROFILES", style = MaterialTheme.typography.labelLarge, color = NeonCyan)
            Spacer(modifier = Modifier.height(8.dp))

            profiles.forEach { profile ->
                CyberCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(profile.name, style = MaterialTheme.typography.titleMedium, color = CyberTextPrimary)
                            Text(profile.description, style = MaterialTheme.typography.bodySmall, color = CyberTextSecondary)
                            Text(profile.type, style = MaterialTheme.typography.labelSmall, color = NeonCyan)
                        }
                        Row {
                            NeonButton("LOAD", {}, Modifier.height(36.dp), NeonCyan)
                            Spacer(modifier = Modifier.width(4.dp))
                            NeonButton("DEL", {}, Modifier.height(36.dp), CyberDanger)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
