package com.example.meliexample.domain.usecases

import com.example.meliexample.data.Resource
import com.example.meliexample.data.models.Product
import com.example.meliexample.data.models.ProductSearchResponse
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

class GetProductByIdUseCaseTest {

    @MockK
    lateinit var productRepository: ProductRepositoryImpl

    private val itemId = "MCO1"

    private val productSearchResponse = listOf(
        ProductSearchResponse(
            code = 200,
            body = mockk<Product>()
        )
    )

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    fun setUp() {
        coEvery {
            productRepository.getProductById(itemId)
        } returns flowOf(Resource.success(productSearchResponse))
    }

    @Test
    fun `When useCase is invoked with param then it returns an ProductSearchResponse with results`() {
        val useCase = GetProductByIdUseCase(productRepository)

        val result = runBlocking {
            useCase(itemId)
        }

        runBlocking {
            result.collectLatest {
                it shouldBeEqualTo Resource.success(productSearchResponse)
            }
        }

        coVerify {
            productRepository.getProductById(itemId)
        }
    }
}
