package com.example.styleswap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.styleswap.data.remote.dto.ProductDto
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun ProductCard(
    product: ProductDto,
    isWishlisted: Boolean,
    onWishlistClick: () -> Unit,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { onClick(product.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "₹ ${product.price}",
                    style = MaterialTheme.typography.bodyMedium
                )

                val rate = product.rating?.rate
                if (rate != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "⭐ $rate",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            IconButton(onClick = onWishlistClick) {
                Icon(
                    imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Wishlist"
                )
            }

        }
    }
}
