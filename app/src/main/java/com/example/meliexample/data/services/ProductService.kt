package com.example.meliexample.data.services

import com.example.meliexample.data.models.ProductSearchResponse
import com.example.meliexample.data.models.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("sites/MCO/search")
    suspend fun searchProducts(@Query("q") querySearch: String) : Response<SearchResponse>

    @GET("items")
    suspend fun getProductById(@Query("ids") itemId: String) : Response<List<ProductSearchResponse>>
}
