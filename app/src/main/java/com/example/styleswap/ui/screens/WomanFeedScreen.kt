package com.example.styleswap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.styleswap.ui.components.ProductCard
import com.example.styleswap.utils.Resource
import com.example.styleswap.viewmodel.FeedViewModel
import com.example.styleswap.viewmodel.WishlistViewModel
import com.example.styleswap.data.local.entity.WishlistEntity

@Composable
fun WomenFeedScreen(viewModel: FeedViewModel,
                    wishlistViewModel: WishlistViewModel,
                    onProductClick: (Int) -> Unit) {

    val state by viewModel.womenProducts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWomenClothing()
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is Resource.Loading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Loading products...")
                }
            }

            is Resource.Error -> {
                val msg = (state as Resource.Error).message
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Error: $msg")
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { viewModel.loadWomenClothing() }) {
                        Text("Retry")
                    }
                }
            }

            is Resource.Success -> {
                val products = (state as Resource.Success).data

                val wishItems by wishlistViewModel.wishlist.collectAsState()
                val wishSet = remember(wishItems) { wishItems.map { it.id }.toSet() }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(products) { product ->
                        val isFav = wishSet.contains(product.id)

                        ProductCard(
                            product = product,
                            isWishlisted = isFav,
                            onWishlistClick = {
                                wishlistViewModel.toggleWishlist(
                                    item = WishlistEntity(
                                        id = product.id,
                                        title = product.title,
                                        price = product.price,
                                        image = product.image,
                                        rating = product.rating?.rate
                                    ),
                                    isWishlisted = isFav
                                )
                            },
                            onClick = onProductClick
                        )
                    }
                }
            }


        }
    }
}
