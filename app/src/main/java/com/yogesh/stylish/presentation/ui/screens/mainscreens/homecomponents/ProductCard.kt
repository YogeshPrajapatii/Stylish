package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yogesh.stylish.R
import com.yogesh.stylish.domain.model.Product

@Composable
fun ProductCard(product: Product, modifier: Modifier
) {
    Card(modifier = Modifier.padding(8.dp)) {

        Column() {
            AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(product.thumbnail)
                .crossfade(true).build(),
                placeholder = painterResource(id = R.drawable.ic_google), // Shows while the image is loading
                error = painterResource(id = R.drawable.ic_google), // Shows if the image fails to load
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f), // This makes the image area a square
                contentScale = ContentScale.Crop)
            Column(modifier = Modifier.padding(8.dp)) {

                Text(text = product.title)
                Text(text = "₹${product.price}")

            }

        }

    }
}
