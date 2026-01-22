package com.example.styleswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.styleswap.data.local.db.DatabaseProvider
import com.example.styleswap.data.remote.api.ApiClient
import com.example.styleswap.data.repository.ProductRepository
import com.example.styleswap.data.repository.WishlistRepository
import com.example.styleswap.ui.navigation.BottomNavApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productRepo = ProductRepository(ApiClient.api)
        val db = DatabaseProvider.getDatabase(this)
        val wishlistRepo = WishlistRepository(db.wishlistDao())

        setContent {
            MaterialTheme {
                Surface {
                    BottomNavApp(
                        productRepo = productRepo,
                        wishlistRepo = wishlistRepo
                    )
                }
            }
        }
    }
}
