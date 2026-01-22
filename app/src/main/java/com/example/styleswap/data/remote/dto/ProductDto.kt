package com.example.styleswap.data.remote.dto

data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDto? = null
)

data class RatingDto(
    val rate: Double,
    val count: Int
)
