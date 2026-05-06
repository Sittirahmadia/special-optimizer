package com.cyberbeast.optimizer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cyberbeast.optimizer.data.local.dao.OptimizerStringDao
import com.cyberbeast.optimizer.data.local.dao.ProfileDao
import com.cyberbeast.optimizer.data.local.entity.OptimizerStringEntity
import com.cyberbeast.optimizer.data.local.entity.ProfileEntity
import com.cyberbeast.optimizer.data.local.entity.GameProfileEntity
import com.cyberbeast.optimizer.utils.Constants

@Database(
    entities = [
        OptimizerStringEntity::class,
        ProfileEntity::class,
        GameProfileEntity::class
    ],
    version = Constants.DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters(MapConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun optimizerStringDao(): OptimizerStringDao
    abstract fun profileDao(): ProfileDao
}
