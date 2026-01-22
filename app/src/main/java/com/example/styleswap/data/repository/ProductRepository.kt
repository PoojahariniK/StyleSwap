package com.example.styleswap.data.repository

import com.example.styleswap.data.remote.api.FakeStoreApi
import com.example.styleswap.data.remote.dto.ProductDto

class ProductRepository(
    private val api: FakeStoreApi
) {

    suspend fun getWomenClothing(): List<ProductDto> {
        return api.getProductsByCategory("women's clothing")
    }

    suspend fun getMenClothing(): List<ProductDto> {
        return api.getProductsByCategory("men's clothing")
    }
    suspend fun getProductById(id: Int): ProductDto {
        return api.getProductById(id)
    }


}
