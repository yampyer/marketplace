package com.example.meliexample.data.repositories

import com.example.meliexample.data.Resource
import com.example.meliexample.data.datasources.ProductRemoteDataSource
import com.example.meliexample.data.models.ProductSearchResponse
import com.example.meliexample.data.models.SearchResponse
import com.example.meliexample.data.services.ProductService
import com.example.meliexample.domain.repositories.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should not be`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryImplTest {

    @MockK
    lateinit var service: ProductService

    private val querySearch = "ps5"
    private val itemId = "MCO1"
    private lateinit var repository: ProductRepository
    private lateinit var remoteDataSource: ProductRemoteDataSource

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    fun setUp() {
        remoteDataSource = ProductRemoteDataSource(service)
        repository = ProductRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `When searchProducts is called then it returns SearchResponse`() = runTest {
        val responseData = mockk<Response<SearchResponse>>(relaxed = true)

        coEvery {
            service.searchProducts(querySearch)
        } returns responseData

        coEvery {
            remoteDataSource.searchProducts(querySearch)
        } returns responseData


        repository.searchProducts(querySearch).first() shouldBeEqualTo Resource.loading(null)
        repository.searchProducts(querySearch).last() `should not be` null
        coVerify { remoteDataSource.searchProducts(querySearch) }
    }

    @Test
    fun `When getProductById is called then it returns ProductSearchResponse`() = runTest {
        val responseData = mockk<Response<List<ProductSearchResponse>>>(relaxed = true)

        coEvery {
            service.getProductById(itemId)
        } returns responseData

        coEvery {
            remoteDataSource.getProductById(itemId)
        } returns responseData

        repository.getProductById(itemId).first() shouldBeEqualTo Resource.loading(null)
        repository.getProductById(itemId).last() `should not be` null
        coVerify { remoteDataSource.getProductById(itemId) }
    }
}