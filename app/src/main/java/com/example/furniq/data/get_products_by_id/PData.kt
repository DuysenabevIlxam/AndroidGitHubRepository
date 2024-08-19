package com.example.furniq.data.get_products_by_id

data class PData(
    val category_id: Int,
    val color: Color,
    val description: Description,
    val discount_price: Any,
    val id: Int,
    val image: Image,
    val images: List<ImageX>,
    val material: Material,
    val name: NameXX,
    val price: Int,
    val quantity: Int,
    val rating: Int,
    val reviews_count: Int,
    val seller: Seller
)