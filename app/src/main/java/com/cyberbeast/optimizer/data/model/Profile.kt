package com.cyberbeast.optimizer.data.model

data class Profile(
    val id: Long = 0,
    val name: String,
    val description: String,
    val type: String,
    val settings: Map<String, String>,
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = false
)
