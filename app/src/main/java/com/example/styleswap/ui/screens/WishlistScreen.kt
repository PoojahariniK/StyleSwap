package com.example.styleswap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.styleswap.viewmodel.WishlistViewModel

@Composable
fun WishlistScreen(viewModel: WishlistViewModel) {
    val items by viewModel.wishlist.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Wishlist",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (items.isEmpty()) {
            Text("No items in wishlist yet ❤️")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items) { item ->
                    Row {
                        AsyncImage(
                            model = item.image,
                            contentDescription = item.title,
                            modifier = Modifier.size(70.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(item.title, maxLines = 2)
                            Text("₹ ${item.price}")
                        }
                    }
                }
            }
        }
    }
}
