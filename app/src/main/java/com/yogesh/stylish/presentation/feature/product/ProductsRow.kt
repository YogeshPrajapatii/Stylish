package com.yogesh.stylish.presentation.feature.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.presentation.feature.home.components.SectionHeader

@Composable
fun ProductsRow(
    title: String,
    products: List<Product>,
    onViewAllClicked: () -> Unit,
    subtitle: String? = null,
    icon: ImageVector? = null,
    headerContainerColor: Color,
    headerContentColor: Color,
    onProductClick: (Int) -> Unit
) {


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column {
        SectionHeader(
            title = title,
            onViewAllClicked = onViewAllClicked,
            subtitle = subtitle,
            icon = icon,
            containerColor = headerContainerColor,
            contentColor = headerContentColor
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    modifier = Modifier.width(screenWidth * 0.55f),
                    onProductClick = onProductClick
                )
            }
        }
    }
}