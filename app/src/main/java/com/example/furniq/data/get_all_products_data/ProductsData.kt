package com.example.furniq.data.get_all_products_data

import com.example.furniq.ui.auth.sign_in.PData

data class ProductsData(
    val data : List<PData>,
    val links: Links,
    val meta: Meta
)