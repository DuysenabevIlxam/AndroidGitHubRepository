package com.example.furniq.data.sign_up_data


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int=0,
    @SerializedName("name")
    val name: String ="",
    @SerializedName("phone")
    val phone: String =""
)