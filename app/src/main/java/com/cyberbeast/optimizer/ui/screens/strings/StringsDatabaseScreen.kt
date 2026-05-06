package com.cyberbeast.optimizer.ui.screens.strings

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberbeast.optimizer.data.model.OptimizerString
import com.cyberbeast.optimizer.ui.components.*
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun StringsDatabaseScreen(
    viewModel: StringsViewModel = hiltViewModel()
) {
    val strings by viewModel.strings.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val lastResult by viewModel.lastResult.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("STRINGS DATABASE", style = MaterialTheme.typography.headlineMedium, color = NeonCyan)
            Text("${strings.size} optimizer strings", style = MaterialTheme.typography.bodyMedium, color = CyberTextSecondary)

            Spacer(modifier = Modifier.height(12.dp))

            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search strings...", color = CyberTextMuted) },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = NeonCyan) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = CyberCardBorder,
                    focusedTextColor = CyberTextPrimary,
                    unfocusedTextColor = CyberTextPrimary
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category Filter
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == "all",
                    onClick = { viewModel.setCategory("all") },
                    label = { Text("All", color = if (selectedCategory == "all") NeonCyan else CyberTextSecondary) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                        selectedLabelColor = NeonCyan
                    )
                )
                categories.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { viewModel.setCategory(cat) },
                        label = { Text(cat.replace("_", " ").uppercase(), color = if (selectedCategory == cat) NeonCyan else CyberTextSecondary) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonCyan.copy(alpha = 0.2f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Strings List
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(strings) { string ->
                    StringCard(string, onApply = { viewModel.applyString(it) })
                }
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = NeonCyan)
            }

            lastResult?.let {
                CyberCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        if (it.success) "Applied!" else "Failed: ${it.stderr}",
                        color = if (it.success) CyberSuccess else CyberDanger
                    )
                }
            }
        }
    }
}

@Composable
private fun StringCard(
    string: OptimizerString,
    onApply: (OptimizerString) -> Unit
) {
    val riskColor = when (string.riskLevel) {
        "extreme" -> CyberDanger
        "high" -> NeonRed
        "medium" -> NeonOrange
        else -> CyberSuccess
    }

    CyberCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(string.name, style = MaterialTheme.typography.titleMedium, color = CyberTextPrimary)
                    Text("${string.table} › ${string.key}", style = MaterialTheme.typography.bodySmall, color = NeonCyan)
                }
                Text(
                    string.riskLevel.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = riskColor,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Text(string.description, style = MaterialTheme.typography.bodySmall, color = CyberTextSecondary, modifier = Modifier.padding(vertical = 4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text("FPS: ${string.fpsBoost}", style = MaterialTheme.typography.labelSmall, color = NeonGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Battery: ${string.batteryImpact}", style = MaterialTheme.typography.labelSmall, color = NeonYellow)
                }
                Row {
                    NeonButton("COPY", {}, Modifier.height(32.dp), NeonBlue)
                    Spacer(modifier = Modifier.width(4.dp))
                    NeonButton("APPLY", { onApply(string) }, Modifier.height(32.dp), NeonCyan)
                }
            }
        }
    }
}
