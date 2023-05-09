package com.example.meliexample.data.repositories

import com.example.meliexample.data.Resource
import com.example.meliexample.data.datasources.ProductRemoteDataSource
import com.example.meliexample.data.models.Product
import com.example.meliexample.domain.BaseRepository
import com.example.meliexample.domain.repositories.ProductRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository, BaseRepository() {
    override suspend fun searchProducts(querySearch: String) = flow {
        emit(Resource.loading())
        emit(safeApiCall { remoteDataSource.searchProducts(querySearch) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getProductById(itemId: String)= flow {
        emit(Resource.loading())
        emit(safeApiCall { remoteDataSource.getProductById(itemId) })
    }.flowOn(Dispatchers.IO)
}
