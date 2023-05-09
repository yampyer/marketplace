package com.example.meliexample.ui.viewmodels

import com.example.meliexample.data.Resource
import com.example.meliexample.data.models.Product
import com.example.meliexample.data.models.ProductSearchResponse
import com.example.meliexample.domain.usecases.GetProductByIdUseCase
import com.example.meliexample.ui.ProductDetailViewModel
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
class ProductDetailViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var getProductByIdUseCase: GetProductByIdUseCase

    private lateinit var viewModel: ProductDetailViewModel

    private val itemId = "MCO1"

    @Before
    override fun setup() {
        super.setup()

        viewModel = ProductDetailViewModel(getProductByIdUseCase)
    }

    @Test
    fun `When getProduct is called then returns Product`() {
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

        val productSearchResponse: Resource<List<ProductSearchResponse>> = Resource.success(
            listOf(
                ProductSearchResponse(code = 200, body = mockProduct)
            )
        )

        coEvery {
            getProductByIdUseCase(itemId)
        } returns flowOf(productSearchResponse)

        runBlocking {
            viewModel.getProduct(itemId).invokeOnCompletion {
                coVerify {
                    getProductByIdUseCase.invoke(itemId)
                }
                viewModel.product.value shouldBeEqualTo Resource.success(productSearchResponse.data?.first())
            }
        }
    }
}