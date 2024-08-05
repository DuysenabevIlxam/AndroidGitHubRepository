package com.example.furniq.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Object {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.furniq.uz/api/v1/customer/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}