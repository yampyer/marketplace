package com.example.meliexample.domain.usecases

import com.example.meliexample.data.Resource
import com.example.meliexample.data.models.ProductSearchResponse
import com.example.meliexample.data.repositories.ProductRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetProductByIdUseCase(private val productRepository: ProductRepositoryImpl) {

    suspend operator fun invoke(itemId: String): Flow<Resource<List<ProductSearchResponse>>> =
        productRepository.getProductById(itemId)
}
