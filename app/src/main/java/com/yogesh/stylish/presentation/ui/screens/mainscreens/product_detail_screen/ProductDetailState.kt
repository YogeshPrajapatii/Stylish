package com.yogesh.stylish.presentation.ui.screens.mainscreens.product_detail_screen

import com.yogesh.stylish.domain.model.Product

data class ProductDetailState(

    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null
)