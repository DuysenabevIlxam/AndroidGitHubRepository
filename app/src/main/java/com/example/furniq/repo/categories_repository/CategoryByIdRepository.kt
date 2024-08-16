package com.example.furniq.repo.categories_repository

import android.util.Log
import com.example.furniq.api.ApiService
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CategoryByIdRepository(private val apiService: ApiService) {

    fun getCategoriesById(id:Int): Flow<SealedClass> = flow {
        try {
            val response = apiService.getCategoriesById(id)
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