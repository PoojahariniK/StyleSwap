package com.example.styleswap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleswap.data.local.entity.WishlistEntity
import com.example.styleswap.data.repository.WishlistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val repo: WishlistRepository
) : ViewModel() {

    val wishlist: StateFlow<List<WishlistEntity>> =
        repo.getWishlist().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleWishlist(item: WishlistEntity, isWishlisted: Boolean) {
        viewModelScope.launch {
            if (isWishlisted) repo.remove(item) else repo.add(item)
        }
    }
}
