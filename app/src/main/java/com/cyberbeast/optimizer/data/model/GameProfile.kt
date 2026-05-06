package com.cyberbeast.optimizer.data.model

data class GameProfile(
    val id: Long = 0,
    val packageName: String,
    val gameName: String,
    val refreshRate: String = "120",
    val performanceMode: Boolean = true,
    val disableNotifications: Boolean = true,
    val blockCalls: Boolean = false,
    val touchBoost: Boolean = true,
    val thermalLimit: String = "high",
    val customSettings: Map<String, String> = emptyMap()
)
