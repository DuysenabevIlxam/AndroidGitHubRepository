package com.example.furniq.repo.favourites

import android.util.Log
import com.example.furniq.api.ApiService
import com.example.furniq.sealedClass.ProfileSealedClass
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetFavouritesRepository (private val apiService: ApiService,private val settings: Settings){

    fun getFavourites(): Flow<SealedClass> = flow {
        try {
            val response = apiService.getFavourites("Bearer ${settings.token}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(SealedClass.SuccessData(it))
                } ?: run {
                    emit(SealedClass.ErrorMessage("Empty response"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                emit(SealedClass.ErrorMessage(errorBody ?: "Unknown error"))
            }
        } catch (e: IOException) {
            emit(SealedClass.NetworkError("Network error"))
        } catch (e: HttpException) {
            emit(SealedClass.ErrorMessage(e.message ?: "HTTP error"))
        }
    }

}