package com.cyberbeast.optimizer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cyberbeast.optimizer.ui.navigation.CyberBottomNav
import com.cyberbeast.optimizer.ui.navigation.Screen
import com.cyberbeast.optimizer.ui.screens.advanced.AdvancedScreen
import com.cyberbeast.optimizer.ui.screens.dashboard.DashboardScreen
import com.cyberbeast.optimizer.ui.screens.gaming.GamingScreen
import com.cyberbeast.optimizer.ui.screens.profile.ProfileScreen
import com.cyberbeast.optimizer.ui.screens.setup.ShizukuSetupScreen
import com.cyberbeast.optimizer.ui.screens.strings.StringsDatabaseScreen
import com.cyberbeast.optimizer.ui.screens.tweaks.TweaksScreen
import com.cyberbeast.optimizer.ui.screens.zalith.ZalithOptimizerScreen
import com.cyberbeast.optimizer.ui.theme.CyberBlack

@Composable
fun CyberBeastAppContent() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { CyberBottomNav(navController) },
        containerColor = CyberBlack
    ) { paddingValues ->
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) { DashboardScreen() }
        composable(Screen.Gaming.route) { GamingScreen() }
        composable(Screen.Zalith.route) { ZalithOptimizerScreen() }
        composable(Screen.Tweaks.route) { TweaksScreen() }
        composable(Screen.Strings.route) { StringsDatabaseScreen() }
        composable(Screen.Advanced.route) { AdvancedScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
        composable(Screen.ShizukuSetup.route) { ShizukuSetupScreen() }
    }
}
