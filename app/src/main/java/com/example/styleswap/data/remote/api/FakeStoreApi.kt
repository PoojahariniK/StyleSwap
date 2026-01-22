package com.example.styleswap.data.remote.api

import com.example.styleswap.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path(value = "category", encoded = true) category: String
    ): List<ProductDto>
    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductDto

}
