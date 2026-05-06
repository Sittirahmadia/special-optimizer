package com.cyberbeast.optimizer.domain.usecase

import com.cyberbeast.optimizer.data.repository.SettingsRepository
import com.cyberbeast.optimizer.shizuku.CommandResult
import javax.inject.Inject

class ApplySettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun applyCyberBeastMode(): List<CommandResult> {
        return settingsRepository.activateCyberBeastMode()
    }

    suspend fun resetAll(): List<CommandResult> {
        return settingsRepository.resetAllSettings()
    }
}
