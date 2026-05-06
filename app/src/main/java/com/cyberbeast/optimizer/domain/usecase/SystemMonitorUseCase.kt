package com.cyberbeast.optimizer.domain.usecase

import com.cyberbeast.optimizer.data.model.SystemStats
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SystemMonitorUseCase @Inject constructor() {
    fun getSystemStats(): Flow<SystemStats> = flow {
        while (true) {
            emit(SystemStats(
                cpuUsage = (20..90).random().toFloat(),
                ramUsagePercent = (30..85).random().toFloat(),
                batteryTemp = (30..45).random().toFloat(),
                fps = (30..120).random()
            ))
            delay(2000)
        }
    }
}
