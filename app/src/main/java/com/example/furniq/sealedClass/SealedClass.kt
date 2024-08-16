package com.example.furniq.sealedClass

import com.example.furniq.data.sign_up_data.GetProfileData

sealed interface SealedClass {
    object Empty : SealedClass
    object Loading : SealedClass
    data class SuccessData<out T>(val data: T) : SealedClass
    data class ErrorMessage<out T>(val error: T) : SealedClass
    data class NetworkError(val msg: String?) : SealedClass
}

sealed interface ProfileSealedClass {
    data class Success(val profileData: GetProfileData) : ProfileSealedClass
    object Empty : ProfileSealedClass
    object Loading : ProfileSealedClass
    data class Error(val message: String) : ProfileSealedClass
    data class SuccessData<out T>(val data: T) : ProfileSealedClass
    data class ErrorMessage<out T>(val error: T) : ProfileSealedClass
    data class NetworkError(val msg: String?) : ProfileSealedClass
}
