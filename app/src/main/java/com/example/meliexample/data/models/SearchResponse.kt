package com.example.meliexample.data.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("site_id") val siteId: String,
    val query: String,
    val results: List<Product>? = emptyList()
)
