package com.example.styleswap.data.local.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "styleswap_db"
        ).build()
    }
}
