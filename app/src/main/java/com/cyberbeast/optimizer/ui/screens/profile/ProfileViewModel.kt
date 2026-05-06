package com.cyberbeast.optimizer.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberbeast.optimizer.data.model.Profile
import com.cyberbeast.optimizer.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles: StateFlow<List<Profile>> = _profiles

    init {
        viewModelScope.launch {
            profileRepository.createDefaultProfiles()
            profileRepository.getAllProfiles().collect {
                _profiles.value = it
            }
        }
    }
}
