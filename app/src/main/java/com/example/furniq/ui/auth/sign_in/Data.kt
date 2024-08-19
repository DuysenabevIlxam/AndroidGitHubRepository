package com.example.furniq.ui.auth.sign_in

import com.example.furniq.data.popular_data.Description
import com.example.furniq.data.popular_data.Image
import com.example.furniq.data.popular_data.Name

data class LData(
    val category_id: Int,
    val description: Description,
    val discount_price: Any,
    val id: Int,
    val image: Image,
    val images: List<Image>,
    val name: Name,
    val price: Int,
    val quantity: Int,
    val rating: Int,
    val reviews_count: Int
):Demo()

data class Data(
    val category_id: Int,
    val description: com.example.furniq.data.latest_data.Description,
    val discount_price: Any,
    val id: Int,
    val image: com.example.furniq.data.latest_data.Image,
    val images: List<com.example.furniq.data.latest_data.Image>,
    val name: com.example.furniq.data.latest_data.Name,
    val price: Int,
    val quantity: Int,
    val rating: Int,
    val reviews_count: Int
): Demo()