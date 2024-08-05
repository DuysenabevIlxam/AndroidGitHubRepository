package com.example.furniq.ui.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.AuthRepo
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInVM (private val authRepo: AuthRepo, private val settings: Settings):ViewModel(){

    private val _loginState = MutableStateFlow<SealedClass>(SealedClass.Empty)
    val loginState: StateFlow<SealedClass> = _loginState

    fun signIn(phone: String, password: String, networkError: String, error: String) {
        _loginState.value = SealedClass.Loading
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.signIn(phone, password, networkError, error).collect { result ->
                _loginState.value = result

            }
        }
    }
}