package com.cyberbeast.optimizer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.TypeConverters

@Entity(tableName = "game_profiles")
@TypeConverters(MapConverter::class)
data class GameProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "package_name")
    val packageName: String,

    @ColumnInfo(name = "game_name")
    val gameName: String,

    @ColumnInfo(name = "refresh_rate")
    val refreshRate: String = "120",

    @ColumnInfo(name = "performance_mode")
    val performanceMode: Boolean = true,

    @ColumnInfo(name = "disable_notifications")
    val disableNotifications: Boolean = true,

    @ColumnInfo(name = "block_calls")
    val blockCalls: Boolean = false,

    @ColumnInfo(name = "touch_boost")
    val touchBoost: Boolean = true,

    @ColumnInfo(name = "thermal_limit")
    val thermalLimit: String = "high",

    @ColumnInfo(name = "custom_settings_json")
    val customSettingsJson: String = "{}"
)
