package com.example.furniq.data.registerTokenData


import com.google.gson.annotations.SerializedName

data class RegisterTokenData(
    @SerializedName("data")
    val data: Data,
    @SerializedName("success")
    val success: Boolean
)