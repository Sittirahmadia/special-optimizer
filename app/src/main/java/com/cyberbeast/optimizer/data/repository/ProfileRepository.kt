package com.cyberbeast.optimizer.data.repository

import com.cyberbeast.optimizer.data.local.dao.ProfileDao
import com.cyberbeast.optimizer.data.local.entity.ProfileEntity
import com.cyberbeast.optimizer.data.model.Profile
import com.cyberbeast.optimizer.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {
    private val gson = Gson()

    fun getAllProfiles(): Flow<List<Profile>> =
        profileDao.getAllProfiles().map { it.map { e -> e.toModel() } }

    suspend fun getActiveProfile(): Profile? = withContext(Dispatchers.IO) {
        profileDao.getActiveProfile()?.toModel()
    }

    suspend fun saveProfile(profile: Profile): Long = withContext(Dispatchers.IO) {
        profileDao.insertProfile(profile.toEntity())
    }

    suspend fun deleteProfile(profile: Profile) = withContext(Dispatchers.IO) {
        profileDao.deleteProfile(profile.toEntity())
    }

    suspend fun setActiveProfile(id: Long) = withContext(Dispatchers.IO) {
        profileDao.deactivateAllProfiles()
        profileDao.setActiveProfile(id)
    }

    suspend fun createDefaultProfiles() = withContext(Dispatchers.IO) {
        if (profileDao.getCount() == 0) {
            val profiles = listOf(
                ProfileEntity(
                    name = "Cyber Beast",
                    description = "Maximum performance, all limits removed",
                    type = Constants.PROFILE_CYBER_BEAST,
                    settingsJson = gson.toJson(mapOf(
                        "refresh_rate" to "120",
                        "animation" to "0.0",
                        "gpu_render" to "1",
                        "renderer" to "skiagl",
                        "thermal" to "0",
                        "bg_process" to "0"
                    ))
                ),
                ProfileEntity(
                    name = "Extreme FPS",
                    description = "Focus on maximum FPS for gaming",
                    type = Constants.PROFILE_EXTREME_FPS,
                    settingsJson = gson.toJson(mapOf(
                        "refresh_rate" to "120",
                        "animation" to "0.0",
                        "gpu_render" to "1",
                        "renderer" to "vulkan",
                        "game_mode" to "1",
                        "touch_boost" to "1"
                    ))
                ),
                ProfileEntity(
                    name = "Balanced",
                    description = "Balance between performance and battery",
                    type = Constants.PROFILE_BALANCED,
                    settingsJson = gson.toJson(mapOf(
                        "refresh_rate" to "90",
                        "animation" to "0.5",
                        "gpu_render" to "1",
                        "bg_process" to "4"
                    ))
                ),
                ProfileEntity(
                    name = "Battery Saver",
                    description = "Maximum battery life",
                    type = Constants.PROFILE_BATTERY,
                    settingsJson = gson.toJson(mapOf(
                        "refresh_rate" to "60",
                        "animation" to "1.0",
                        "gpu_render" to "0",
                        "bg_process" to "4",
                        "doze" to "1"
                    ))
                )
            )
            profiles.forEach { profileDao.insertProfile(it) }
        }
    }

    private fun ProfileEntity.toModel() = Profile(
        id = id,
        name = name,
        description = description,
        type = type,
        settings = gson.fromJson(settingsJson, Map::class.java) as Map<String, String>,
        createdAt = createdAt,
        isActive = isActive
    )

    private fun Profile.toEntity() = ProfileEntity(
        id = id,
        name = name,
        description = description,
        type = type,
        settingsJson = gson.toJson(settings),
        createdAt = createdAt,
        isActive = isActive
    )
}
