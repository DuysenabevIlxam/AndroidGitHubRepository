package com.example.furniq.repo.auth_repo

import android.util.Log
import com.example.furniq.api.ApiService
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LatestRepository(private val apiService: ApiService) {
    fun getLatest(): Flow<SealedClass> = flow {
        try {
            val response = apiService.getLatest()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(SealedClass.SuccessData(it))
                    Log.d("TTT", "repository:--->${response} ")
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