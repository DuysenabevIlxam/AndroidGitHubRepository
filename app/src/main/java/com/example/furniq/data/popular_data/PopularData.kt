package com.example.furniq.data.popular_data

import com.example.furniq.ui.auth.sign_in.Data

data class PopularData(
    val data: List<Data>,
    val links: Links,
    val meta: Meta
)