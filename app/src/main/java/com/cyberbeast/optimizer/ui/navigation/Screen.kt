package com.cyberbeast.optimizer.ui.navigation

sealed class Screen(val route: String, val title: String, val icon: String) {
    object Dashboard : Screen("dashboard", "Dashboard", "dashboard")
    object Gaming : Screen("gaming", "Gaming", "sports_esports")
    object Zalith : Screen("zalith", "Zalith", "videogame_asset")
    object Tweaks : Screen("tweaks", "Tweaks", "tune")
    object Strings : Screen("strings", "Strings DB", "storage")
    object Advanced : Screen("advanced", "Advanced", "code")
    object Profile : Screen("profile", "Profile", "account_circle")
    object ShizukuSetup : Screen("shizuku_setup", "Shizuku Setup", "settings")
}
