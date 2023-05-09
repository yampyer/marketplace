package com.example.meliexample.domain.usecases

import com.example.meliexample.data.Resource
import com.example.meliexample.data.models.SearchResponse
import com.example.meliexample.data.repositories.ProductRepositoryImpl
import kotlinx.coroutines.flow.Flow

class SearchProductsUseCase(private val productRepository: ProductRepositoryImpl) {

    suspend operator fun invoke(querySearch: String): Flow<Resource<SearchResponse>> =
        productRepository.searchProducts(querySearch)
}
