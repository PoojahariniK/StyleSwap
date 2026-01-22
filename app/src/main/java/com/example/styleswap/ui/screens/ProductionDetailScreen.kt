package com.example.styleswap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.styleswap.utils.Resource
import com.example.styleswap.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel
) {
    val state by viewModel.product.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Error -> {
                Text(text = "Error: ${(state as Resource.Error).message}")
            }

            is Resource.Success -> {
                val product = (state as Resource.Success).data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "₹ ${product.price}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    val rate = product.rating?.rate
                    if (rate != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "⭐ $rate (${product.rating.count} reviews)")
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
