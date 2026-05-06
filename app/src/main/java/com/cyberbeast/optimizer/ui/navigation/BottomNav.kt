package com.cyberbeast.optimizer.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cyberbeast.optimizer.ui.theme.*

@Composable
fun CyberBottomNav(navController: NavController) {
    val items = listOf(
        Screen.Dashboard,
        Screen.Gaming,
        Screen.Zalith,
        Screen.Tweaks,
        Screen.Strings,
        Screen.Advanced,
        Screen.Profile
    )

    NavigationBar(
        containerColor = CyberDark,
        contentColor = NeonCyan,
        tonalElevation = 0.dp
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(getIcon(screen.icon), contentDescription = screen.title, tint = if (currentRoute == screen.route) NeonCyan else CyberTextSecondary) },
                label = { Text(screen.title, style = MaterialTheme.typography.labelSmall, color = if (currentRoute == screen.route) NeonCyan else CyberTextSecondary) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NeonCyan,
                    selectedTextColor = NeonCyan,
                    indicatorColor = CyberDarkElevated,
                    unselectedIconColor = CyberTextSecondary,
                    unselectedTextColor = CyberTextSecondary
                )
            )
        }
    }
}

@Composable
private fun getIcon(name: String) = when (name) {
    "dashboard" -> Icons.Default.Dashboard
    "sports_esports" -> Icons.Default.Gamepad
    "videogame_asset" -> Icons.Default.VideogameAsset
    "tune" -> Icons.Default.Tune
    "storage" -> Icons.Default.Storage
    "code" -> Icons.Default.Code
    "account_circle" -> Icons.Default.AccountCircle
    else -> Icons.Default.Settings
}
