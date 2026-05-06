package com.cyberbeast.optimizer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "optimizer_strings")
data class OptimizerStringEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "key")
    val key: String,

    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "table_name")
    val table: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "fps_boost")
    val fpsBoost: String,

    @ColumnInfo(name = "battery_impact")
    val batteryImpact: String,

    @ColumnInfo(name = "risk_level")
    val riskLevel: String,

    @ColumnInfo(name = "is_custom")
    val isCustom: Boolean = false,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "popularity")
    val popularity: Int = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
