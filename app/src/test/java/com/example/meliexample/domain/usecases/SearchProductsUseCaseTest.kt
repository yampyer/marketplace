package com.example.meliexample.domain.usecases

import com.example.meliexample.data.Resource
import com.example.meliexample.data.models.Product
import com.example.meliexample.data.models.SearchResponse
import com.example.meliexample.data.repositories.ProductRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class SearchProductsUseCaseTest {

    @MockK
    lateinit var productRepository: ProductRepositoryImpl

    private val querySearch = "ps5"

    private val productsResponse = SearchResponse(
        siteId = "MCO",
        query = querySearch,
        results = listOf(mockk<Product>())
    )

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    fun setUp() {
        coEvery {
            productRepository.searchProducts(querySearch)
        } returns flowOf(Resource.success(productsResponse))
    }

    @Test
    fun `When useCase is invoked with param then it returns an SearchResponse with results`() {
        val useCase = SearchProductsUseCase(productRepository)

        val result = runBlocking {
            useCase(querySearch)
        }

        runBlocking {
            result.collectLatest {
                it shouldBeEqualTo Resource.success(productsResponse)
            }
        }

        coVerify {
            productRepository.searchProducts(querySearch)
        }
    }
}
