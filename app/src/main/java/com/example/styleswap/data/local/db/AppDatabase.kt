package com.example.styleswap.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.styleswap.data.local.dao.WishlistDao
import com.example.styleswap.data.local.entity.WishlistEntity

@Database(
    entities = [WishlistEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}
