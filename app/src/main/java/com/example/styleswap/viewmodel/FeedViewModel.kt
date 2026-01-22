package com.example.styleswap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleswap.data.remote.dto.ProductDto
import com.example.styleswap.data.repository.ProductRepository
import com.example.styleswap.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class FeedViewModel(
    private val repo: ProductRepository
) : ViewModel() {

    private val _womenProducts =
        MutableStateFlow<Resource<List<ProductDto>>>(Resource.Loading)

    val womenProducts: StateFlow<Resource<List<ProductDto>>> = _womenProducts

    fun loadWomenClothing() {
        viewModelScope.launch {
            _womenProducts.value = Resource.Loading
            try {
                val data = repo.getWomenClothing()
                Log.d("StyleSwap", "Women products: ${data.size}")
                _womenProducts.value = Resource.Success(data)
            } catch (e: Exception) {
                Log.e("StyleSwap", "API Error: ${e.message}", e)
                _womenProducts.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    private val _menProducts =
        MutableStateFlow<Resource<List<ProductDto>>>(Resource.Loading)

    val menProducts: StateFlow<Resource<List<ProductDto>>> = _menProducts

    fun loadMenClothing() {
        viewModelScope.launch {
            _menProducts.value = Resource.Loading
            try {
                val data = repo.getMenClothing()
                _menProducts.value = Resource.Success(data)
            } catch (e: Exception) {
                _menProducts.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

}
