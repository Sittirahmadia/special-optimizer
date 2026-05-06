package com.cyberbeast.optimizer.data.repository

import com.cyberbeast.optimizer.data.local.entity.GameProfileEntity
import com.cyberbeast.optimizer.data.model.GameProfile
import com.cyberbeast.optimizer.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameProfileRepository @Inject constructor() {
    private val gson = Gson()

    fun getDefaultGameProfiles(): List<GameProfile> {
        return Constants.GAME_PACKAGES.mapIndexed { index, pkg ->
            GameProfile(
                id = index.toLong(),
                packageName = pkg,
                gameName = getGameName(pkg),
                refreshRate = "120",
                performanceMode = true,
                disableNotifications = true,
                blockCalls = false,
                touchBoost = true,
                thermalLimit = "high"
            )
        }
    }

    private fun getGameName(packageName: String): String {
        return when {
            packageName.contains("pubg") || packageName.contains("ig") -> "PUBG Mobile"
            packageName.contains("legends") -> "Mobile Legends"
            packageName.contains("Genshin") -> "Genshin Impact"
            packageName.contains("callofduty") || packageName.contains("codm") -> "COD Mobile"
            packageName.contains("freefire") -> "Free Fire"
            packageName.contains("fortnite") -> "Fortnite"
            packageName.contains("fifa") -> "FIFA Mobile"
            packageName.contains("clashroyale") -> "Clash Royale"
            packageName.contains("brawlstars") -> "Brawl Stars"
            packageName.contains("wuthering") -> "Wuthering Waves"
            packageName.contains("starRail") -> "Honkai Star Rail"
            packageName.contains("honor") -> "Honor of Kings"
            else -> packageName.substringAfterLast(".")
        }
    }
}
