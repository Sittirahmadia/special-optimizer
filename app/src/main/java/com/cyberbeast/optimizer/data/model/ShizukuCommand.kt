package com.cyberbeast.optimizer.data.model

data class ShizukuCommand(
    val id: String,
    val command: String,
    val description: String,
    val requiresReboot: Boolean = false,
    val riskLevel: String = "low",
    val category: String = "system"
)
