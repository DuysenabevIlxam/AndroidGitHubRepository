package com.example.furniq.ui.auth.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.AuthRepo
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State

class SignUpVM(private val authRepo: AuthRepo,private val settings: Settings):ViewModel() {

    private val _signUpState= MutableStateFlow<SealedClass>(SealedClass.Empty)
     val signUpState:StateFlow<SealedClass> = _signUpState

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    init {
        _isLoggedIn.value = settings.isUserLoggedIn()
    }

    fun singUp(name:String,phone: String,password:String,error: String,networkError:String){
        _signUpState.value=SealedClass.Loading
        viewModelScope.launch (Dispatchers.IO){
            try {
                authRepo.signUp(name,phone,password,error,networkError).collect{result->
                    _signUpState.value=result
                }
            }catch (e:Exception){
                _signUpState.value=SealedClass.ErrorMessage(e.message?: "Qate Qate Qate")
            }
        }
    }

}