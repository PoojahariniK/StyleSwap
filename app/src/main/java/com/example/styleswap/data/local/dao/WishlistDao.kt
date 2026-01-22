package com.example.styleswap.data.local.dao

import androidx.room.*
import com.example.styleswap.data.local.entity.WishlistEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface WishlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WishlistEntity): Long // Return Long instead of Unit

    @Delete
    suspend fun delete(item: WishlistEntity): Int // Return Int instead of Unit

    @Query("SELECT * FROM wishlist")
    fun getAllWishlist(): Flow<List<WishlistEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist WHERE id = :productId)")
    fun isWishlisted(productId: Int): Flow<Boolean>
}