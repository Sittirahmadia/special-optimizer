package com.cyberbeast.optimizer.data.model

data class SystemStats(
    val cpuUsage: Float = 0f,
    val cpuFreq: String = "0 MHz",
    val ramUsed: Long = 0,
    val ramTotal: Long = 0,
    val ramUsagePercent: Float = 0f,
    val batteryLevel: Int = 0,
    val batteryTemp: Float = 0f,
    val batteryVoltage: Int = 0,
    val fps: Int = 0,
    val gpuUsage: Float = 0f,
    val gpuFreq: String = "0 MHz",
    val thermalStatus: String = "Normal",
    val networkType: String = "Unknown",
    val networkSpeed: String = "0 KB/s"
)
