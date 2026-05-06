package com.cyberbeast.optimizer.ui.screens.gaming

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.cyberbeast.optimizer.data.repository.GameProfileRepository
import androidx.compose.runtime.remember
import com.cyberbeast.optimizer.ui.components.*
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun GamingScreen() {
    val gameRepo = remember { GameProfileRepository() }
    val games = remember { gameRepo.getDefaultGameProfiles() }

    Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("GAMING OPTIMIZER", style = MaterialTheme.typography.headlineMedium, color = NeonCyan)
            Text("Game profiles & boost", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Actions
            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Text("QUICK ACTIONS", style = MaterialTheme.typography.labelMedium, color = NeonCyan)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    NeonButton("GAME TURBO", {}, Modifier.weight(1f), NeonMagenta)
                    NeonButton("FPS MONITOR", {}, Modifier.weight(1f), NeonGreen)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Game List
            Text("SUPPORTED GAMES", style = MaterialTheme.typography.labelLarge, color = NeonCyan)
            Spacer(modifier = Modifier.height(8.dp))

            games.take(10).forEach { game ->
                CyberCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(game.gameName, style = MaterialTheme.typography.titleMedium, color = CyberTextPrimary)
                            Text(game.packageName, style = MaterialTheme.typography.bodySmall, color = CyberTextMuted)
                        }
                        NeonButton("BOOST", {}, Modifier.height(36.dp), NeonGreen)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
