package com.cyberbeast.optimizer.data.model

data class OptimizerString(
    val id: Long = 0,
    val name: String,
    val key: String,
    val value: String,
    val table: String,
    val description: String,
    val category: String,
    val fpsBoost: String,
    val batteryImpact: String,
    val riskLevel: String,
    val isCustom: Boolean = false,
    val isFavorite: Boolean = false,
    val popularity: Int = 0
)
