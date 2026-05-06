package com.cyberbeast.optimizer.shizuku

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import rikka.shizuku.Shizuku
import rikka.shizuku.ShizukuBinderWrapper
import rikka.shizuku.SystemServiceHelper
import timber.log.Timber
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.io.BufferedReader

object ShizukuHelper {

    fun isInstalled(context: Context): Boolean {
        return try {
            context.packageManager.getPackageInfo("moe.shizuku.privileged.api", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun isRunning(): Boolean {
        return try {
            Shizuku.pingBinder()
        } catch (e: Exception) {
            false
        }
    }

    fun checkPermission(): Boolean {
        return try {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        } catch (e: Exception) {
            false
        }
    }

    fun requestPermission(requestCode: Int) {
        try {
            Shizuku.requestPermission(requestCode)
        } catch (e: Exception) {
            Timber.e(e, "Failed to request Shizuku permission")
        }
    }

    fun executeCommand(command: String): CommandResult {
        return try {
            if (!isRunning() || !checkPermission()) {
                return CommandResult(false, "", "Shizuku not available or permission denied")
            }

            val process = Shizuku.newProcess(
                arrayOf("sh", "-c", command),
                null,
                null
            )

            val stdout = StringBuilder()
            val stderr = StringBuilder()

            val stdoutReader = BufferedReader(InputStreamReader(process.inputStream))
            val stderrReader = BufferedReader(InputStreamReader(process.errorStream))

            var line: String?
            while (stdoutReader.readLine().also { line = it } != null) {
                stdout.append(line).append("
")
            }

            while (stderrReader.readLine().also { line = it } != null) {
                stderr.append(line).append("
")
            }

            val exitCode = process.waitFor()

            CommandResult(
                success = exitCode == 0,
                stdout = stdout.toString().trim(),
                stderr = stderr.toString().trim()
            )
        } catch (e: Exception) {
            Timber.e(e, "Command execution failed: $command")
            CommandResult(false, "", e.message ?: "Unknown error")
        }
    }

    fun executeCommands(commands: List<String>): List<CommandResult> {
        return commands.map { executeCommand(it) }
    }

    fun getShizukuVersion(): String {
        return try {
            Shizuku.getVersion().toString()
        } catch (e: Exception) {
            "Unknown"
        }
    }

    fun getUid(): Int {
        return try {
            Shizuku.getUid()
        } catch (e: Exception) {
            -1
        }
    }
}

data class CommandResult(
    val success: Boolean,
    val stdout: String,
    val stderr: String
)
