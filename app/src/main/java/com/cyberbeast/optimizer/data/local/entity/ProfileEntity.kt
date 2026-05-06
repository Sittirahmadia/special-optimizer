package com.cyberbeast.optimizer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.TypeConverters

@Entity(tableName = "profiles")
@TypeConverters(MapConverter::class)
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "settings_json")
    val settingsJson: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "is_active")
    val isActive: Boolean = false
)
