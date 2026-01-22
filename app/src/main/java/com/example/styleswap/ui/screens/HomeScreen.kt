package com.example.styleswap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.styleswap.data.local.entity.WishlistEntity
import com.example.styleswap.ui.components.ProductCard
import com.example.styleswap.utils.Resource
import com.example.styleswap.utils.SortOption
import com.example.styleswap.viewmodel.FeedViewModel
import com.example.styleswap.viewmodel.WishlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FeedViewModel,
    wishlistViewModel: WishlistViewModel,
    onProductClick: (Int) -> Unit
) {

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Women", "Men")

    var query by remember { mutableStateOf("") }
    var sortOption by remember { mutableStateOf(SortOption.NONE) }
    var sortExpanded by remember { mutableStateOf(false) }

    val womenState by viewModel.womenProducts.collectAsState()
    val menState by viewModel.menProducts.collectAsState()

    val wishItems by wishlistViewModel.wishlist.collectAsState()
    val wishSet = remember(wishItems) { wishItems.map { it.id }.toSet() }

    LaunchedEffect(Unit) {
        viewModel.loadWomenClothing()
        viewModel.loadMenClothing()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(title = { Text("StyleSwap") })

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search products...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { })
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(onClick = { sortExpanded = true }) {
                Text("Sort: ${sortOption.label}")
            }

            DropdownMenu(
                expanded = sortExpanded,
                onDismissRequest = { sortExpanded = false }
            ) {
                SortOption.values().forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label) },
                        onClick = {
                            sortOption = option
                            sortExpanded = false
                        }
                    )
                }
            }
        }

        val currentState = if (selectedTab == 0) womenState else menState

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            when (currentState) {

                is Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Error -> {
                    Text("Error: ${(currentState as Resource.Error).message}")
                }

                is Resource.Success -> {
                    val originalProducts = (currentState as Resource.Success).data

                    val filtered = originalProducts.filter { product ->
                        product.title.contains(query, ignoreCase = true)
                    }

                    val products = when (sortOption) {
                        SortOption.NONE -> filtered
                        SortOption.PRICE_LOW_HIGH -> filtered.sortedBy { it.price }
                        SortOption.PRICE_HIGH_LOW -> filtered.sortedByDescending { it.price }
                        SortOption.RATING_HIGH_LOW -> filtered.sortedByDescending { it.rating?.rate ?: 0.0 }
                    }

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
                                onClick = { onProductClick(product.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
