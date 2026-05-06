package com.cyberbeast.optimizer.ui.screens.zalith

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberbeast.optimizer.shizuku.CommandExecutor
import com.cyberbeast.optimizer.shizuku.CommandResult
import com.cyberbeast.optimizer.shizuku.ShizukuHelper
import com.cyberbeast.optimizer.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ZalithViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val commandExecutor: CommandExecutor
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _lastResult = MutableStateFlow<CommandResult?>(null)
    val lastResult: StateFlow<CommandResult?> = _lastResult

    private val _zalithStatus = MutableStateFlow(false)
    val zalithStatus: StateFlow<Boolean> = _zalithStatus

    private val zalithPackages = listOf(
        Constants.ZALITH_PACKAGE,
        Constants.ZALITH_DEBUG_PACKAGE
    )

    init {
        checkZalithInstallation()
    }

    private fun checkZalithInstallation() {
        viewModelScope.launch {
            val pm = context.packageManager
            _zalithStatus.value = zalithPackages.any { pkg ->
                try {
                    pm.getPackageInfo(pkg, 0)
                    true
                } catch (e: PackageManager.NameNotFoundException) {
                    false
                }
            }
        }
    }

    // ========== RENDERER ==========
    fun setRenderer(renderer: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val commands = listOf(
                // Set renderer via shared prefs
                "setprop zalith.renderer $renderer",
                "setprop pojav.renderer $renderer",
                // Also set in Zalith's data directory
                "echo '$renderer' > /sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft/renderer.txt",
                // Update environment variable
                "setprop persist.zalith.renderer $renderer"
            )
            commands.forEach { commandExecutor.executeShellCommand(it) }
            _lastResult.value = CommandResult(true, "Renderer set to $renderer", "")
            _isLoading.value = false
        }
    }

    // ========== JVM MEMORY ==========
    fun setJvmMemory(size: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val commands = listOf(
                "setprop zalith.jvm.maxmemory $size",
                "setprop pojav.jvm.maxmemory $size",
                "echo '-Xmx$size' > /sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft/jvm_args.txt"
            )
            commands.forEach { commandExecutor.executeShellCommand(it) }
            _lastResult.value = CommandResult(true, "JVM memory set to $size", "")
            _isLoading.value = false
        }
    }

    // ========== JVM ARGS TOGGLES ==========
    fun toggleG1GC(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            val arg = if (enabled) "-XX:+UseG1GC" else ""
            commandExecutor.executeShellCommand("setprop zalith.jvm.g1gc '$arg'")
            _lastResult.value = CommandResult(true, "G1GC ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    fun togglePreTouch(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            val arg = if (enabled) "-XX:+AlwaysPreTouch" else ""
            commandExecutor.executeShellCommand("setprop zalith.jvm.pretouch '$arg'")
            _lastResult.value = CommandResult(true, "AlwaysPreTouch ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    fun toggleDisableExplicitGC(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            val arg = if (enabled) "-XX:+DisableExplicitGC" else ""
            commandExecutor.executeShellCommand("setprop zalith.jvm.disableexplicitgc '$arg'")
            _lastResult.value = CommandResult(true, "DisableExplicitGC ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    fun toggleLargePages(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            val arg = if (enabled) "-XX:+UseLargePages" else ""
            commandExecutor.executeShellCommand("setprop zalith.jvm.largepages '$arg'")
            _lastResult.value = CommandResult(true, "LargePages ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    // ========== PERFORMANCE TOGGLES ==========
    fun toggleSustainablePerf(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            commandExecutor.executeShellCommand(
                "settings put system zalith_sustainable_perf ${if(enabled) "1" else "0"}"
            )
            _lastResult.value = CommandResult(true, "Sustainable perf ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    fun toggleHighPerfCores(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            // Force big cores on Helio G81 Ultra
            val commands = if (enabled) {
                listOf(
                    "echo 1 > /sys/devices/system/cpu/cpu4/online",
                    "echo 1 > /sys/devices/system/cpu/cpu5/online",
                    "echo 1 > /sys/devices/system/cpu/cpu6/online",
                    "echo 1 > /sys/devices/system/cpu/cpu7/online",
                    "echo performance > /sys/devices/system/cpu/cpu4/cpufreq/scaling_governor",
                    "echo performance > /sys/devices/system/cpu/cpu5/cpufreq/scaling_governor",
                    "echo performance > /sys/devices/system/cpu/cpu6/cpufreq/scaling_governor",
                    "echo performance > /sys/devices/system/cpu/cpu7/cpufreq/scaling_governor"
                )
            } else {
                listOf(
                    "echo schedutil > /sys/devices/system/cpu/cpu4/cpufreq/scaling_governor",
                    "echo schedutil > /sys/devices/system/cpu/cpu5/cpufreq/scaling_governor",
                    "echo schedutil > /sys/devices/system/cpu/cpu6/cpufreq/scaling_governor",
                    "echo schedutil > /sys/devices/system/cpu/cpu7/cpufreq/scaling_governor"
                )
            }
            commands.forEach { commandExecutor.executeShellCommand(it) }
            _lastResult.value = CommandResult(true, "High perf cores ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    fun toggleReduceResolution(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            commandExecutor.executeShellCommand(
                "settings put system zalith_resolution_scale ${if(enabled) "0.75" else "1.0"}"
            )
            _lastResult.value = CommandResult(true, "Resolution scale ${if(enabled) "75%" else "100%"}", "")
            _isLoading.value = false
        }
    }

    fun toggleFastMode(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            commandExecutor.executeShellCommand(
                "settings put system zalith_fast_mode ${if(enabled) "1" else "0"}"
            )
            _lastResult.value = CommandResult(true, "Fast mode ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    fun toggleVSync(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            // Disable VSync for uncapped FPS
            commandExecutor.executeShellCommand(
                "settings put system zalith_vsync ${if(enabled) "1" else "0"}"
            )
            _lastResult.value = CommandResult(true, "VSync ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    fun toggleOptiFineCompat(enabled: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            val commands = if (enabled) {
                listOf(
                    "setprop zalith.optifine.compat 1",
                    "setprop zalith.shader.support 1",
                    "settings put system zalith_optifine 1"
                )
            } else {
                listOf(
                    "setprop zalith.optifine.compat 0",
                    "setprop zalith.shader.support 0",
                    "settings put system zalith_optifine 0"
                )
            }
            commands.forEach { commandExecutor.executeShellCommand(it) }
            _lastResult.value = CommandResult(true, "OptiFine compat ${if(enabled) "enabled" else "disabled"}", "")
            _isLoading.value = false
        }
    }

    // ========== ONE TAP MAX BOOST ==========
    fun applyZalithMaxBoost() {
        viewModelScope.launch {
            _isLoading.value = true
            val commands = listOf(
                // Renderer: GL4ES for best compatibility
                "setprop zalith.renderer gl4es",
                "setprop pojav.renderer gl4es",

                // JVM Memory: 4GB for Redmi 14C
                "setprop zalith.jvm.maxmemory 4G",
                "setprop pojav.jvm.maxmemory 4G",

                // JVM Args: Performance optimized
                "setprop zalith.jvm.args '-XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:+AlwaysPreTouch -XX:+DisableExplicitGC -XX:MaxGCPauseMillis=30 -XX:GCPauseIntervalMillis=150'",

                // Force high performance cores
                "echo 1 > /sys/devices/system/cpu/cpu4/online",
                "echo 1 > /sys/devices/system/cpu/cpu5/online",
                "echo 1 > /sys/devices/system/cpu/cpu6/online",
                "echo 1 > /sys/devices/system/cpu/cpu7/online",
                "echo performance > /sys/devices/system/cpu/cpu4/cpufreq/scaling_governor",
                "echo performance > /sys/devices/system/cpu/cpu5/cpufreq/scaling_governor",
                "echo performance > /sys/devices/system/cpu/cpu6/cpufreq/scaling_governor",
                "echo performance > /sys/devices/system/cpu/cpu7/cpufreq/scaling_governor",

                // System optimizations
                "settings put global sys_vm_stats 0",
                "settings put global cached_apps_freezer disabled",
                "settings put global app_standby_enabled 0",

                // Disable thermal throttling for gaming
                "settings put global thermal_limit 0",

                // Zalith specific
                "settings put system zalith_sustainable_perf 1",
                "settings put system zalith_fast_mode 1",
                "settings put system zalith_vsync 0",
                "settings put system zalith_resolution_scale 0.85",

                // Network optimization for multiplayer
                "settings put global net_dns1 1.1.1.1",
                "settings put global net_dns2 1.0.0.1",
                "settings put global tcp_default_init_rwnd 60",

                // RAM optimization
                "settings put global background_process_limit 0",
                "am kill-all",

                // Save config to file
                "echo 'renderer=gl4es' > /sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft/cyberbeast_config.txt",
                "echo 'memory=4G' >> /sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft/cyberbeast_config.txt",
                "echo 'jvm_args=-XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:+AlwaysPreTouch -XX:+DisableExplicitGC' >> /sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft/cyberbeast_config.txt"
            )

            commands.forEach { cmd ->
                Timber.d("Executing: $cmd")
                commandExecutor.executeShellCommand(cmd)
            }

            _lastResult.value = CommandResult(true, "Zalith Max Boost applied! Restart Zalith Launcher.", "")
            _isLoading.value = false
        }
    }

    // ========== CLEAR CACHE ==========
    fun clearZalithCache() {
        viewModelScope.launch {
            _isLoading.value = true
            val commands = listOf(
                "rm -rf /sdcard/Android/data/com.movtery.zalithlauncher/cache/*",
                "rm -rf /data/data/com.movtery.zalithlauncher/cache/*",
                "am force-stop com.movtery.zalithlauncher",
                "am force-stop com.movtery.zalithlauncher.debug"
            )
            commands.forEach { commandExecutor.executeShellCommand(it) }
            _lastResult.value = CommandResult(true, "Zalith cache cleared!", "")
            _isLoading.value = false
        }
    }

    // ========== RESET ZALITH ==========
    fun resetZalithSettings() {
        viewModelScope.launch {
            _isLoading.value = true
            val commands = listOf(
                "setprop zalith.renderer ''",
                "setprop pojav.renderer ''",
                "setprop zalith.jvm.maxmemory ''",
                "setprop zalith.jvm.args ''",
                "settings delete system zalith_sustainable_perf",
                "settings delete system zalith_fast_mode",
                "settings delete system zalith_vsync",
                "settings delete system zalith_resolution_scale",
                "rm -f /sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft/cyberbeast_config.txt",
                "rm -f /sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft/renderer.txt",
                "rm -f /sdcard/Android/data/com.movtery.zalithlauncher/files/.minecraft/jvm_args.txt"
            )
            commands.forEach { commandExecutor.executeShellCommand(it) }
            _lastResult.value = CommandResult(true, "Zalith settings reset to default!", "")
            _isLoading.value = false
        }
    }
}
