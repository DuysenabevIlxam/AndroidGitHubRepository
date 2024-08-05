package com.example.furniq.data.get_all_products_data

data class ProductsData(
    val data : List<PData>,
    val links: Links,
    val meta: Meta
)