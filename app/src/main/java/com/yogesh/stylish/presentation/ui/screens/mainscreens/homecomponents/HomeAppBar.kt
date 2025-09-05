package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar() {
    CenterAlignedTopAppBar(


        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
        title = {
            Image(painter = painterResource(id = R.drawable.img_app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.height(32.dp))
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "Menu",
                    modifier = Modifier.size(36.dp))
            }
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.img_profile_pic),
                contentDescription = "Profile Logo",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape), 
                contentScale = ContentScale.Crop
            )
        })
}