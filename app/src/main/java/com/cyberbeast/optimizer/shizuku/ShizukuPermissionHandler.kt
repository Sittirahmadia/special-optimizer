package com.cyberbeast.optimizer.shizuku

import android.content.pm.PackageManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.*
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import rikka.shizuku.Shizuku
import timber.log.Timber

class ShizukuPermissionHandler(
    private val context: Context
) : DefaultLifecycleObserver {

    private val _permissionGranted = mutableStateOf(false)
    val permissionGranted: State<Boolean> = _permissionGranted

    private val _shizukuRunning = mutableStateOf(false)
    val shizukuRunning: State<Boolean> = _shizukuRunning

    private val binderReceivedListener = Shizuku.OnBinderReceivedListener {
        updateStatus()
    }

    private val binderDeadListener = Shizuku.OnBinderDeadListener {
        _shizukuRunning.value = false
        _permissionGranted.value = false
    }

    private val requestPermissionResultListener = 
        Shizuku.OnRequestPermissionResultListener { requestCode, grantResult ->
            _permissionGranted.value = grantResult == PackageManager.PERMISSION_GRANTED
        }

    init {
        Shizuku.addBinderReceivedListener(binderReceivedListener)
        Shizuku.addBinderDeadListener(binderDeadListener)
        Shizuku.addRequestPermissionResultListener(requestPermissionResultListener)
        updateStatus()
    }

    fun updateStatus() {
        _shizukuRunning.value = ShizukuHelper.isRunning()
        _permissionGranted.value = ShizukuHelper.checkPermission()
    }

    fun requestPermission(requestCode: Int = 8710) {
        if (ShizukuHelper.isRunning() && !ShizukuHelper.checkPermission()) {
            ShizukuHelper.requestPermission(requestCode)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Shizuku.removeBinderReceivedListener(binderReceivedListener)
        Shizuku.removeBinderDeadListener(binderDeadListener)
        Shizuku.removeRequestPermissionResultListener(requestPermissionResultListener)
    }
}
