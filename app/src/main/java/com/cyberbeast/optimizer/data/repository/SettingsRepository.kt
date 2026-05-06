package com.cyberbeast.optimizer.data.repository

import com.cyberbeast.optimizer.shizuku.CommandExecutor
import com.cyberbeast.optimizer.shizuku.CommandResult
import com.cyberbeast.optimizer.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val commandExecutor: CommandExecutor
) {

    // ========== REFRESH RATE ==========
    suspend fun lockRefreshRate(rate: String): CommandResult = withContext(Dispatchers.IO) {
        val commands = listOf(
            "settings put system min_refresh_rate $rate",
            "settings put system peak_refresh_rate $rate",
            "settings put system user_refresh_rate $rate"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Refresh rate locked to ${rate}Hz", "")
    }

    // ========== ANIMATION SCALE ==========
    suspend fun setAnimationScales(scale: String): CommandResult = withContext(Dispatchers.IO) {
        val commands = listOf(
            "settings put global window_animation_scale $scale",
            "settings put global transition_animation_scale $scale",
            "settings put global animator_duration_scale $scale"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Animation scales set to $scale", "")
    }

    // ========== GPU RENDERING ==========
    suspend fun forceGpuRendering(enabled: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val value = if (enabled) "1" else "0"
        commandExecutor.executeSettingsPut("global", "debug.hwui.force_gpu_rendering", value)
    }

    // ========== RENDERER PIPELINE ==========
    suspend fun setRenderer(renderer: String): CommandResult = withContext(Dispatchers.IO) {
        val commands = when (renderer) {
            "opengl" -> listOf(
                "setprop debug.hwui.renderer opengl",
                "setprop debug.hwui.use_vulkan false",
                "settings put global debug.hwui.renderer opengl"
            )
            "vulkan" -> listOf(
                "setprop debug.hwui.renderer vulkan",
                "setprop debug.hwui.use_vulkan true",
                "settings put global debug.hwui.renderer vulkan"
            )
            "skiagl" -> listOf(
                "setprop debug.hwui.renderer skiagl",
                "setprop debug.hwui.use_vulkan false",
                "settings put global debug.hwui.renderer skiagl"
            )
            "skiavk" -> listOf(
                "setprop debug.hwui.renderer skiavk",
                "setprop debug.hwui.use_vulkan true",
                "settings put global debug.hwui.renderer skiavk"
            )
            else -> listOf("setprop debug.hwui.renderer $renderer")
        }
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Renderer set to $renderer", "")
    }

    // ========== PERFORMANCE MODE ==========
    suspend fun forcePerformanceMode(enabled: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val value = if (enabled) "1" else "0"
        val commands = listOf(
            "settings put global sys_vm_stats 0",
            "settings put global sys_vm_stats $value",
            "settings put global cached_apps_freezer enabled",
            "settings put global app_standby_enabled $value",
            "settings put global forced_app_standby_enabled $value",
            "settings put global app_auto_restriction_enabled $value"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Performance mode ${if(enabled) "enabled" else "disabled"}", "")
    }

    // ========== POWER KEEPER ==========
    suspend fun disablePowerKeeper(disable: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val action = if (disable) "disable-user" else "enable"
        commandExecutor.executePmCommand("$action com.miui.powerkeeper")
    }

    // ========== BATTERY OPTIMIZATION ==========
    suspend fun disableBatteryOptimization(disable: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val value = if (disable) "0" else "1"
        val commands = listOf(
            "settings put global adaptive_battery_management_enabled $value",
            "settings put global app_standby_enabled $value",
            "settings put global forced_app_standby_enabled $value",
            "settings put global app_auto_restriction_enabled $value",
            "settings put global cached_apps_freezer ${if(disable) "disabled" else "enabled"}",
            "settings put global enable_battery_saver_user_restrictions $value"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Battery optimization ${if(disable) "disabled" else "enabled"}", "")
    }

    // ========== RAM MANAGEMENT ==========
    suspend fun aggressiveRamManager(enabled: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val commands = if (enabled) {
            listOf(
                "settings put global sys_vm_stats 0",
                "settings put global sys_lmk_report 0",
                "settings put global sys_vm_stats 0",
                "am kill-all",
                "settings put global background_process_limit 0"
            )
        } else {
            listOf(
                "settings put global background_process_limit 4",
                "settings put global sys_vm_stats 1"
            )
        }
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Aggressive RAM ${if(enabled) "enabled" else "disabled"}", "")
    }

    // ========== TOUCH RESPONSE ==========
    suspend fun optimizeTouchResponse(enabled: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val commands = if (enabled) {
            listOf(
                "settings put system pointer_speed 7",
                "settings put system touch_debounce 0",
                "settings put system long_press_timeout 200",
                "settings put system multi_press_timeout 150",
                "settings put system tap_duration 0",
                "settings put system touch_sensitivity 10"
            )
        } else {
            listOf(
                "settings put system pointer_speed 0",
                "settings put system long_press_timeout 400",
                "settings put system multi_press_timeout 300"
            )
        }
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Touch response ${if(enabled) "optimized" else "reset"}", "")
    }

    // ========== NETWORK OPTIMIZATION ==========
    suspend fun optimizeNetwork(enabled: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val commands = if (enabled) {
            listOf(
                "settings put global tcp_default_init_rwnd 60",
                "settings put global net_tcp_buffersize_default 4096,87380,256960,4096,16384,256960",
                "settings put global net_tcp_buffersize_wifi 524288,1048576,2097152,262144,524288,1048576",
                "settings put global net_tcp_buffersize_lte 524288,1048576,2097152,262144,524288,1048576",
                "settings put global net_dns1 1.1.1.1",
                "settings put global net_dns2 1.0.0.1",
                "settings put global net_dns3 8.8.8.8",
                "settings put global tether_dun_required 0"
            )
        } else {
            listOf(
                "settings delete global tcp_default_init_rwnd",
                "settings delete global net_tcp_buffersize_default",
                "settings delete global net_dns1"
            )
        }
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Network ${if(enabled) "optimized" else "reset"}", "")
    }

    // ========== THERMAL CONTROL ==========
    suspend fun reduceThermalThrottling(enabled: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val commands = if (enabled) {
            listOf(
                "settings put global thermal_limit 0",
                "echo 0 > /sys/class/thermal/thermal_zone0/trip_point_0_temp",
                "echo 0 > /sys/class/thermal/thermal_zone1/trip_point_0_temp",
                "settings put global sys_vm_stats 0"
            )
        } else {
            listOf(
                "settings delete global thermal_limit"
            )
        }
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Thermal throttling ${if(enabled) "reduced" else "reset"}", "")
    }

    // ========== DISABLE ADS ==========
    suspend fun disableAds(disable: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val packages = listOf(
            "com.miui.msa.global",
            "com.miui.analytics",
            "com.miui.systemAdSolution",
            "com.xiaomi.joyose",
            "com.miui.hybrid"
        )
        val action = if (disable) "disable-user" else "enable"
        packages.forEach { pkg ->
            commandExecutor.executePmCommand("$action $pkg")
        }

        val settings = listOf(
            "settings put global device_provisioned 1",
            "settings put global user_setup_complete 1"
        )
        settings.forEach { commandExecutor.executeShellCommand(it) }

        CommandResult(true, "MIUI Ads ${if(disable) "disabled" else "enabled"}", "")
    }

    // ========== CPU GOVERNOR ==========
    suspend fun setCpuGovernor(governor: String): CommandResult = withContext(Dispatchers.IO) {
        val commands = listOf(
            "echo $governor > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor",
            "echo $governor > /sys/devices/system/cpu/cpu1/cpufreq/scaling_governor",
            "echo $governor > /sys/devices/system/cpu/cpu2/cpufreq/scaling_governor",
            "echo $governor > /sys/devices/system/cpu/cpu3/cpufreq/scaling_governor",
            "echo $governor > /sys/devices/system/cpu/cpu4/cpufreq/scaling_governor",
            "echo $governor > /sys/devices/system/cpu/cpu5/cpufreq/scaling_governor",
            "echo $governor > /sys/devices/system/cpu/cpu6/cpufreq/scaling_governor",
            "echo $governor > /sys/devices/system/cpu/cpu7/cpufreq/scaling_governor"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "CPU Governor set to $governor", "")
    }

    // ========== I/O SCHEDULER ==========
    suspend fun setIoScheduler(scheduler: String): CommandResult = withContext(Dispatchers.IO) {
        val commands = listOf(
            "echo $scheduler > /sys/block/mmcblk0/queue/scheduler",
            "echo $scheduler > /sys/block/mmcblk1/queue/scheduler",
            "echo $scheduler > /sys/block/sda/queue/scheduler",
            "echo $scheduler > /sys/block/sdb/queue/scheduler",
            "echo 0 > /sys/block/mmcblk0/queue/iostats",
            "echo 0 > /sys/block/sda/queue/iostats",
            "echo 1024 > /sys/block/mmcblk0/queue/read_ahead_kb",
            "echo 1024 > /sys/block/sda/queue/read_ahead_kb"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "I/O Scheduler set to $scheduler", "")
    }

    // ========== ZRAM ==========
    suspend fun configureZram(size: String): CommandResult = withContext(Dispatchers.IO) {
        val commands = listOf(
            "swapoff /dev/block/zram0",
            "echo 1 > /sys/block/zram0/reset",
            "echo $size > /sys/block/zram0/disksize",
            "mkswap /dev/block/zram0",
            "swapon /dev/block/zram0"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "ZRAM configured to $size", "")
    }

    // ========== DISABLE BLUR ==========
    suspend fun disableBlurEffects(disable: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val value = if (disable) "0" else "1"
        val commands = listOf(
            "settings put system show_surface_updates $value",
            "settings put global disable_hw_overlays $value",
            "settings put system sys_vm_stats ${if(disable) "0" else "1"}"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "Blur effects ${if(disable) "disabled" else "enabled"}", "")
    }

    // ========== MSAA ==========
    suspend fun forceMsaa(enabled: Boolean): CommandResult = withContext(Dispatchers.IO) {
        val value = if (enabled) "1" else "0"
        val commands = listOf(
            "settings put global debug.hwui.force_msaa $value",
            "settings put global debug.hwui.disable_vsync $value",
            "settings put global debug.hwui.show_dirty_regions $value"
        )
        commands.forEach { commandExecutor.executeShellCommand(it) }
        CommandResult(true, "4x MSAA ${if(enabled) "forced" else "disabled"}", "")
    }

    // ========== ONE TAP CYBER BEAST ==========
    suspend fun activateCyberBeastMode(): List<CommandResult> = withContext(Dispatchers.IO) {
        val commands = listOf(
            // Refresh Rate
            "settings put system min_refresh_rate 120",
            "settings put system peak_refresh_rate 120",
            "settings put system user_refresh_rate 120",
            // Animation
            "settings put global window_animation_scale 0.0",
            "settings put global transition_animation_scale 0.0",
            "settings put global animator_duration_scale 0.0",
            // Performance
            "settings put global sys_vm_stats 0",
            "settings put global cached_apps_freezer disabled",
            "settings put global app_standby_enabled 0",
            "settings put global forced_app_standby_enabled 0",
            // GPU
            "settings put global debug.hwui.force_gpu_rendering 1",
            "setprop debug.hwui.renderer skiagl",
            // RAM
            "settings put global background_process_limit 0",
            "am kill-all",
            // Touch
            "settings put system pointer_speed 7",
            "settings put system long_press_timeout 200",
            // Network
            "settings put global net_dns1 1.1.1.1",
            "settings put global net_dns2 1.0.0.1",
            // Disable Power Keeper
            "pm disable-user com.miui.powerkeeper",
            // Disable Analytics
            "pm disable-user com.miui.analytics"
        )
        commands.map { commandExecutor.executeShellCommand(it) }
    }

    // ========== RESET ALL ==========
    suspend fun resetAllSettings(): List<CommandResult> = withContext(Dispatchers.IO) {
        val commands = listOf(
            "settings put global window_animation_scale 1.0",
            "settings put global transition_animation_scale 1.0",
            "settings put global animator_duration_scale 1.0",
            "settings put system min_refresh_rate 60",
            "settings put system peak_refresh_rate 120",
            "settings put global debug.hwui.force_gpu_rendering 0",
            "settings put global background_process_limit 4",
            "settings put system pointer_speed 0",
            "settings put system long_press_timeout 400",
            "settings put global sys_vm_stats 1",
            "settings put global cached_apps_freezer enabled",
            "settings put global app_standby_enabled 1",
            "settings put global forced_app_standby_enabled 1",
            "pm enable com.miui.powerkeeper",
            "pm enable com.miui.analytics"
        )
        commands.map { commandExecutor.executeShellCommand(it) }
    }

    // ========== REBOOT ==========
    suspend fun rebootDevice(): CommandResult = withContext(Dispatchers.IO) {
        commandExecutor.rebootDevice()
    }

    // ========== GET CURRENT VALUE ==========
    suspend fun getCurrentValue(table: String, key: String): String = withContext(Dispatchers.IO) {
        val result = commandExecutor.executeSettingsGet(table, key)
        result.stdout.ifEmpty { "Not set" }
    }
}
