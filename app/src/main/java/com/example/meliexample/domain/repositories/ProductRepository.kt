package com.example.meliexample.domain.repositories

import com.example.meliexample.data.Resource
import com.example.meliexample.data.models.Product
import com.example.meliexample.data.models.ProductSearchResponse
import com.example.meliexample.data.models.SearchResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun searchProducts(querySearch: String): Flow<Resource<SearchResponse>>

    suspend fun getProductById(itemId: String): Flow<Resource<List<ProductSearchResponse>>>
}
