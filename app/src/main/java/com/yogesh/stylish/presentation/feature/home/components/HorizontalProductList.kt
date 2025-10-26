package com.yogesh.stylish.presentation.feature.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.domain.model.Product

@Composable
fun HorizontalProductList(
    products: List<Product>,
    onProductClick: (Int) -> Unit
) {
    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(items = products) { product ->
            ProductCard(
                product = product,
                modifier = Modifier.width(225.dp),
                onProductClick = onProductClick
            )
        }
    }
}