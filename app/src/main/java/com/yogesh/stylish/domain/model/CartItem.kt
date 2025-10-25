package com.yogesh.stylish.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(val product: Product, val quantity: Int = 1
)
