package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ProductsRow(

    title: String,
    products: List<Product>,
    OnViewAllClicked: () -> Unit,
    OnProductClicked: (products) -> Unit

) {

    Column() {

        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {

            items(items= products) { product ->

                ProductCard(product = product, modifier = Modifier.width(160.dp)

                )


            }

        }

    }

}