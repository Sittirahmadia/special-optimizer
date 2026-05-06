package com.cyberbeast.optimizer.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberbeast.optimizer.data.model.SystemStats
import com.cyberbeast.optimizer.data.repository.SettingsRepository
import com.cyberbeast.optimizer.shizuku.ShizukuHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _systemStats = MutableStateFlow(SystemStats())
    val systemStats: StateFlow<SystemStats> = _systemStats

    private val _shizukuStatus = MutableStateFlow(false)
    val shizukuStatus: StateFlow<Boolean> = _shizukuStatus

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _activeProfile = MutableStateFlow("Cyber Beast")
    val activeProfile: StateFlow<String> = _activeProfile

    init {
        startMonitoring()
    }

    private fun startMonitoring() {
        viewModelScope.launch {
            while (true) {
                _shizukuStatus.value = ShizukuHelper.isRunning() && ShizukuHelper.checkPermission()
                _systemStats.value = SystemStats(
                    cpuUsage = (30..85).random().toFloat(),
                    ramUsagePercent = (40..80).random().toFloat(),
                    batteryTemp = (32..42).random().toFloat(),
                    fps = (55..120).random()
                )
                delay(2000)
            }
        }
    }

    fun activateCyberBeastMode() {
        viewModelScope.launch {
            _isLoading.value = true
            settingsRepository.activateCyberBeastMode()
            _activeProfile.value = "Cyber Beast"
            _isLoading.value = false
        }
    }

    fun resetAll() {
        viewModelScope.launch {
            _isLoading.value = true
            settingsRepository.resetAllSettings()
            _activeProfile.value = "Default"
            _isLoading.value = false
        }
    }

    fun reboot() {
        viewModelScope.launch {
            settingsRepository.rebootDevice()
        }
    }
}
