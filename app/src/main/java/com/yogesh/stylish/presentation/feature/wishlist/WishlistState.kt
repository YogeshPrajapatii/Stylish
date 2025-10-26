package com.yogesh.stylish.presentation.feature.wishlist

import com.yogesh.stylish.domain.model.Product

data class WishlistState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)
