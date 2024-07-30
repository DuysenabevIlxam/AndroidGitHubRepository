package com.example.furniq.repo.auth_repo

import com.example.furniq.api.ApiService
import com.example.furniq.sealedClass.ProfileSealedClass
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AuthRepo (private val apiService: ApiService, private val settings: Settings) {

    fun signUp(name: String, phone: String, password: String, error: String, networkError: String): Flow<SealedClass> = flow {

        try {
            val response = apiService.signUp(name, phone, password)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(SealedClass.SuccessData(it))
                    settings.token = it.data.token
                } ?: emit(SealedClass.ErrorMessage("Empty response body"))
            } else {
                emit(SealedClass.ErrorMessage(error))
            }

        } catch (e: IOException) {
            emit(SealedClass.NetworkError(networkError))
        } catch (e: HttpException) {
            emit(SealedClass.ErrorMessage(e.message ?: error))
        }
    }

    fun getProfile(): Flow<ProfileSealedClass> = flow {
        try {
            val response = apiService.getProfile("Bearer ${settings.token}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ProfileSealedClass.Success(it))
                } ?: run {
                    emit(ProfileSealedClass.Error("Empty response"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                emit(ProfileSealedClass.Error(errorBody ?: "Unknown error"))
            }
        } catch (e: IOException) {
            emit(ProfileSealedClass.NetworkError("Network error"))
        } catch (e: HttpException) {
            emit(ProfileSealedClass.Error(e.message ?: "HTTP error"))
        }
    }

    fun logOut(): Flow<String> = flow {
        try {
            val response = apiService.logOut("Bearer ${settings.token}")
            if (response.isSuccessful) {
                settings.clearToken()
                emit(response.body()?.toString() ?: "Empty response")
            } else {
                emit("Unknown error")
            }
        } catch (e: IOException) {
            emit(e.message ?: "Network error")
        } catch (e: HttpException) {
            emit(e.message ?: "HTTP error")
        }
    }

    fun signIn(phone: String, password: String, networkError: String, error: String): Flow<SealedClass> =
        flow {
            try {
                val response = apiService.signIn(phone, password)
                if (response.isSuccessful) {
                    emit(SealedClass.SuccessData(response.body()))
                    settings.token = response.body()!!.data.token
                } else {
                    emit(SealedClass.ErrorMessage(error))
                }
            } catch (e: IOException) {
                emit(SealedClass.NetworkError(networkError))
            } catch (e: HttpException) {
                emit(SealedClass.ErrorMessage(error))
            }
        }

}
