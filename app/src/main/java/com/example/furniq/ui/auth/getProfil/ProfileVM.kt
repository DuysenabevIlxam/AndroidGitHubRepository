package com.example.furniq.ui.auth.getProfil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.AuthRepo
import com.example.furniq.sealedClass.ProfileSealedClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileVM (private val authRepo: AuthRepo): ViewModel() {

    private val _profileState = MutableStateFlow<ProfileSealedClass>(ProfileSealedClass.Loading)
    val profileState: StateFlow<ProfileSealedClass> = _profileState
    /*
    private val _logoutResult = MutableStateFlow<String>("")
    val logoutResult: StateFlow<String> get() = _logoutResult  */

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            authRepo.getProfile().collect { result ->
                _profileState.value = result
            }
        }

    }

    fun logOut() {
        viewModelScope.launch {
            authRepo.logOut().collect { result ->
            }
        }
    }
}