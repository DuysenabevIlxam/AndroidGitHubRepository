package com.example.furniq.data.sign_in_data


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String=""
)