package com.example.styleswap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleswap.data.remote.dto.ProductDto
import com.example.styleswap.data.repository.ProductRepository
import com.example.styleswap.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repo: ProductRepository
) : ViewModel() {

    private val _product = MutableStateFlow<Resource<ProductDto>>(Resource.Loading)
    val product: StateFlow<Resource<ProductDto>> = _product

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            _product.value = Resource.Loading
            try {
                val data = repo.getProductById(id)
                _product.value = Resource.Success(data)
            } catch (e: Exception) {
                _product.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}
