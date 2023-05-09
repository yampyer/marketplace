package com.example.meliexample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliexample.data.Resource
import com.example.meliexample.data.models.Product
import com.example.meliexample.domain.usecases.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _product: MutableLiveData<Resource<Product>> = MutableLiveData()
    val product: LiveData<Resource<Product>>
        get() = _product

    fun getProduct(itemId: String) = viewModelScope.launch {
        getProductByIdUseCase.invoke(itemId).collect { response ->
            _product.value = Resource(response.status, response.data?.first()?.body, response.message)
        }
    }
}
