package com.cyberbeast.optimizer.domain.usecase

import com.cyberbeast.optimizer.data.model.Profile
import com.cyberbeast.optimizer.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileManagerUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    fun getProfiles(): Flow<List<Profile>> = profileRepository.getAllProfiles()

    suspend fun activateProfile(profile: Profile) {
        profileRepository.setActiveProfile(profile.id)
    }
}
