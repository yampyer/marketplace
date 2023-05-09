package com.example.meliexample.data.datasources

import com.example.meliexample.data.services.ProductService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val productService: ProductService) {
    suspend fun searchProducts(querySearch: String) =
        productService.searchProducts(querySearch)

    suspend fun getProductById(itemId: String) =
        productService.getProductById(itemId)
}
