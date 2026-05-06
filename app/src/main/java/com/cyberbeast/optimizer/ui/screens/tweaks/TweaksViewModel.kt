package com.cyberbeast.optimizer.ui.screens.tweaks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberbeast.optimizer.data.repository.SettingsRepository
import com.cyberbeast.optimizer.shizuku.CommandResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweaksViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _lastResult = MutableStateFlow<CommandResult?>(null)
    val lastResult: StateFlow<CommandResult?> = _lastResult

    fun setRefreshRate(rate: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.lockRefreshRate(rate)
            _isLoading.value = false
        }
    }

    fun setAnimationScale(scale: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.setAnimationScales(scale)
            _isLoading.value = false
        }
    }

    fun toggleGpuRendering(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.forceGpuRendering(enabled)
            _isLoading.value = false
        }
    }

    fun setRenderer(renderer: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.setRenderer(renderer)
            _isLoading.value = false
        }
    }

    fun togglePerformanceMode(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.forcePerformanceMode(enabled)
            _isLoading.value = false
        }
    }

    fun togglePowerKeeper(disable: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.disablePowerKeeper(disable)
            _isLoading.value = false
        }
    }

    fun toggleBatteryOptimization(disable: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.disableBatteryOptimization(disable)
            _isLoading.value = false
        }
    }

    fun toggleRamManager(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.aggressiveRamManager(enabled)
            _isLoading.value = false
        }
    }

    fun optimizeTouch(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.optimizeTouchResponse(enabled)
            _isLoading.value = false
        }
    }

    fun optimizeNetwork(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.optimizeNetwork(enabled)
            _isLoading.value = false
        }
    }

    fun reduceThermal(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.reduceThermalThrottling(enabled)
            _isLoading.value = false
        }
    }

    fun toggleAds(disable: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.disableAds(disable)
            _isLoading.value = false
        }
    }

    fun setCpuGovernor(governor: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.setCpuGovernor(governor)
            _isLoading.value = false
        }
    }

    fun setIoScheduler(scheduler: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.setIoScheduler(scheduler)
            _isLoading.value = false
        }
    }

    fun forceMsaa(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.forceMsaa(enabled)
            _isLoading.value = false
        }
    }

    fun disableBlur(disable: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = settingsRepository.disableBlurEffects(disable)
            _isLoading.value = false
        }
    }
}
