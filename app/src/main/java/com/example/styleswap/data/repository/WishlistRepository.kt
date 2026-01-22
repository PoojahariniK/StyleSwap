package com.example.styleswap.data.repository

import com.example.styleswap.data.local.dao.WishlistDao
import com.example.styleswap.data.local.entity.WishlistEntity
import kotlinx.coroutines.flow.Flow

class WishlistRepository(
    private val dao: WishlistDao
) {
    fun getWishlist(): Flow<List<WishlistEntity>> = dao.getAllWishlist()

    fun isWishlisted(productId: Int): Flow<Boolean> = dao.isWishlisted(productId)

    suspend fun add(item: WishlistEntity) = dao.insert(item)

    suspend fun remove(item: WishlistEntity) = dao.delete(item)
}
