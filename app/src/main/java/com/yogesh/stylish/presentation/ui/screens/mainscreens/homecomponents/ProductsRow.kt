package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.domain.model.Product


@Composable
fun ProductsRow(

    title: String,
    products: List<Product>,
    onViewAllClicked: () -> Unit,
    

) {

    Column() {


        SectionHeader(title = title, onViewAllClicked = onViewAllClicked)


        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {

            items(items = products) { product ->

                ProductCard(product = product, modifier = Modifier.width(160.dp)

                )


            }

        }

    }

}