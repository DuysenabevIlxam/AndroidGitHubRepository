package com.example.furniq.data.sign_in_data


import com.google.gson.annotations.SerializedName

data class LoginTokenData(
    @SerializedName("data")
    val data: Data,
    @SerializedName("success")
    val success: Boolean
)