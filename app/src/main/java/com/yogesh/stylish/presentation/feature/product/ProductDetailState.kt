package com.yogesh.stylish.presentation.feature.product

import com.yogesh.stylish.domain.model.Product

data class ProductDetailState(

    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null,
    val isInWishlist: Boolean = false
)