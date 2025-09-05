package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.domain.model.Product

@Composable
fun ProductsRow(
    title: String,
    products: List<Product>,
    onViewAllClicked: () -> Unit, 
    subtitle: String? = null,
    icon: ImageVector? = null,
    headerContainerColor: Color,
    headerContentColor: Color
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        SectionHeader(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = title,
            onViewAllClicked = onViewAllClicked,
            subtitle = subtitle,
            icon = icon,
            containerColor = headerContainerColor,
            contentColor = headerContentColor
        )

        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(items = products) { product ->
                ProductCard(
                    product = product,
                    modifier = Modifier.width(240.dp)
                )
            }
        }
    }
}