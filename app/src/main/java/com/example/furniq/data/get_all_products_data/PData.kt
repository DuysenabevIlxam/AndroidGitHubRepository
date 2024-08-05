package com.example.furniq.data.get_all_products_data

data class PData(
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
)