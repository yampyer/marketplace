package com.example.meliexample.ui.viewmodels

import com.example.meliexample.data.Resource
import com.example.meliexample.data.models.Product
import com.example.meliexample.data.models.SearchResponse
import com.example.meliexample.domain.usecases.SearchProductsUseCase
import com.example.meliexample.ui.ProductListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductListViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var searchProductUseCase: SearchProductsUseCase

    private lateinit var viewModel: ProductListViewModel

    private val queryString = "ps5"

    @Before
    override fun setup() {
        super.setup()

        viewModel = ProductListViewModel(searchProductUseCase)
    }

    @Test
    fun `When searchProduct is called then returns searchResponse`() {
        val mockProduct = Product(
            id = "MCO1",
            title = "PS5 Console",
            condition = "new",
            thumbnail = "url",
            thumbnailId = "thumbnail1",
            catalogProductId = "catProd1",
            catalogListing = true,
            categoryId = "cat1",
            listingTypeId = "listingType1",
            permalink = "link",
            buyingMode = "buyer",
            domainId = "domain1",
            siteId = "MCO",
            currencyId = "COP",
            orderBackend = 1,
            price = 3000000.0,
            originalPrice = 3000000.0,
            salePrice = null,
            soldQuantity = 1,
            availableQuantity = 10,
            officialStoreId = "officialStore1",
            useThumbnailId = true,
            acceptsMercadoPago = true,
            inventoryId = "inventory1",
            winnerItemId = "winnerItem1",
            discounts = null,
            stopTime = "stopTime",
            tags = emptyList(),
            shipping = null
        )

        val searchResponse: Resource<SearchResponse> = Resource.success(
            SearchResponse(
                "MCO", "PS5", listOf(
                    mockProduct,
                    mockProduct.copy(id = "MCO2", catalogProductId = "catProd2", title = "PS5 Digital"),
                    mockProduct.copy(id = "MCO3", catalogProductId = "catProd3", title = "PS5 used"),
                )
            )
        )

        coEvery {
            searchProductUseCase(queryString)
        } returns flowOf(searchResponse)

        runBlocking {
            viewModel.searchProducts(queryString).invokeOnCompletion {
                coVerify {
                    searchProductUseCase.invoke(queryString)
                }
                viewModel.searchResponse.value shouldBeEqualTo Resource.success(searchResponse.data)
            }
        }
    }
}