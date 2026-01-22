package com.example.styleswap.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.styleswap.data.repository.ProductRepository
import com.example.styleswap.data.repository.WishlistRepository
import com.example.styleswap.ui.screens.HomeScreen
import com.example.styleswap.ui.screens.ProductDetailScreen
import com.example.styleswap.ui.screens.WishlistScreen
import com.example.styleswap.viewmodel.FeedViewModel
import com.example.styleswap.viewmodel.ProductDetailViewModel
import com.example.styleswap.viewmodel.WishlistViewModel

sealed class BottomRoute(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : BottomRoute("home", "Home", Icons.Filled.Home)
    object Wishlist : BottomRoute("wishlist", "Wishlist", Icons.Filled.Favorite)
}

@Composable
fun BottomNavApp(
    productRepo: ProductRepository,
    wishlistRepo: WishlistRepository
) {
    val navController = rememberNavController()

    val feedViewModel = FeedViewModel(productRepo)
    val detailViewModel = ProductDetailViewModel(productRepo)
    val wishlistViewModel = WishlistViewModel(wishlistRepo)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(BottomRoute.Home, BottomRoute.Wishlist)
                items.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = BottomRoute.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomRoute.Home.route) {
                HomeScreen(
                    viewModel = feedViewModel,
                    wishlistViewModel = wishlistViewModel,
                    onProductClick = { id ->
                        navController.navigate("detail/$id")
                    }
                )
            }

            composable(BottomRoute.Wishlist.route) {
                WishlistScreen(viewModel = wishlistViewModel)
            }

            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                ProductDetailScreen(productId = id, viewModel = detailViewModel)
            }
        }
    }
}
