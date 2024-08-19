package com.example.furniq.data.latest_data

import com.example.furniq.ui.auth.sign_in.LData

data class LatestData(
    val `data`: List<LData>,
    val links: Links,
    val meta: Meta
)