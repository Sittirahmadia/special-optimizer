package com.cyberbeast.optimizer.shizuku

import com.cyberbeast.optimizer.utils.Constants
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandExecutor @Inject constructor() {

    fun executeSettingsPut(table: String, key: String, value: String): CommandResult {
        val command = "${Constants.CMD_SETTINGS_PUT} $table $key $value"
        Timber.d("Executing: $command")
        return ShizukuHelper.executeCommand(command)
    }

    fun executeSettingsGet(table: String, key: String): CommandResult {
        val command = "${Constants.CMD_SETTINGS_GET} $table $key"
        return ShizukuHelper.executeCommand(command)
    }

    fun executeSetProp(key: String, value: String): CommandResult {
        val command = "${Constants.CMD_SETPROP} $key $value"
        return ShizukuHelper.executeCommand(command)
    }

    fun executeGetProp(key: String): CommandResult {
        val command = "${Constants.CMD_GETPROP} $key"
        return ShizukuHelper.executeCommand(command)
    }

    fun executePmCommand(args: String): CommandResult {
        val command = "${Constants.CMD_PM} $args"
        return ShizukuHelper.executeCommand(command)
    }

    fun executeAmCommand(args: String): CommandResult {
        val command = "${Constants.CMD_AM} $args"
        return ShizukuHelper.executeCommand(command)
    }

    fun executeDumpsys(service: String): CommandResult {
        val command = "${Constants.CMD_DUMPSYS} $service"
        return ShizukuHelper.executeCommand(command)
    }

    fun executeShellCommand(command: String): CommandResult {
        return ShizukuHelper.executeCommand(command)
    }

    fun executeBatchCommands(commands: List<String>): List<CommandResult> {
        return commands.map { cmd ->
            Timber.d("Batch executing: $cmd")
            ShizukuHelper.executeCommand(cmd)
        }
    }

    fun setAnimationScale(scale: String): CommandResult {
        return executeSettingsPut("global", "window_animation_scale", scale)
    }

    fun setTransitionScale(scale: String): CommandResult {
        return executeSettingsPut("global", "transition_animation_scale", scale)
    }

    fun setAnimatorScale(scale: String): CommandResult {
        return executeSettingsPut("global", "animator_duration_scale", scale)
    }

    fun setRefreshRate(rate: String): CommandResult {
        return executeSettingsPut("system", "min_refresh_rate", rate)
    }

    fun setPeakRefreshRate(rate: String): CommandResult {
        return executeSettingsPut("system", "peak_refresh_rate", rate)
    }

    fun forceGpuRendering(enabled: Boolean): CommandResult {
        val value = if (enabled) "1" else "0"
        return executeSettingsPut("global", "debug.hwui.force_gpu_rendering", value)
    }

    fun setRenderer(renderer: String): CommandResult {
        return executeSetProp("debug.hwui.renderer", renderer)
    }

    fun disablePowerKeeper(enabled: Boolean): CommandResult {
        val action = if (enabled) "disable" else "enable"
        return executePmCommand("$action-user com.miui.powerkeeper")
    }

    fun clearCache(packageName: String): CommandResult {
        return executePmCommand("clear $packageName")
    }

    fun rebootDevice(): CommandResult {
        return executeShellCommand("reboot")
    }
}
