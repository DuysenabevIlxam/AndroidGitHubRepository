package com.example.furniq.ui.auth.sign_in

import com.example.furniq.data.favourites_data.Description
import com.example.furniq.data.favourites_data.Image
import com.example.furniq.data.favourites_data.Name

data class FData(
    val category_id: Int,
    val description: Description,
    val discount_price: Any,
    val id: Int,
    val image: Image,
    val name: Name,
    val price: Int,
    val quantity: Int,
    val rating: Int,
    val reviews_count: Int
) :Demo()