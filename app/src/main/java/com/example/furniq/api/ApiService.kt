package com.example.furniq.api

import com.example.furniq.data.registerTokenData.RegisterTokenData
import com.example.furniq.data.sign_in_data.LoginTokenData
import com.example.furniq.data.sign_up_data.GetProfileData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun signUp(@Field("name") name: String, @Field("phone") phone: String, @Field("password") password: String): Response<RegisterTokenData>

    @GET("getMe")
    suspend fun getProfile(@Header("Authorization") token: String): Response<GetProfileData>

    @POST("logout")
    suspend fun logOut(@Header("Authorization") token: String): Response<Void>

    @FormUrlEncoded
    @POST("login")
    suspend fun signIn(@Field("phone") phone: String, @Field("password") password: String): Response<LoginTokenData>

}
