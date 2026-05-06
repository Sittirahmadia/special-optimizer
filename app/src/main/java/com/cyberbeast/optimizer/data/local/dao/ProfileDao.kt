package com.cyberbeast.optimizer.data.local.dao

import androidx.room.*
import com.cyberbeast.optimizer.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles ORDER BY created_at DESC")
    fun getAllProfiles(): Flow<List<ProfileEntity>>

    @Query("SELECT * FROM profiles WHERE is_active = 1 LIMIT 1")
    suspend fun getActiveProfile(): ProfileEntity?

    @Query("SELECT * FROM profiles WHERE id = :id")
    suspend fun getProfileById(id: Long): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity): Long

    @Update
    suspend fun updateProfile(profile: ProfileEntity)

    @Delete
    suspend fun deleteProfile(profile: ProfileEntity)

    @Query("UPDATE profiles SET is_active = 0")
    suspend fun deactivateAllProfiles()

    @Query("UPDATE profiles SET is_active = 1 WHERE id = :id")
    suspend fun setActiveProfile(id: Long)

    @Query("SELECT COUNT(*) FROM profiles")
    suspend fun getCount(): Int
}
