package com.yogesh.stylish.presentation.ui.screens.mainscreens.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.yogesh.stylish.R
import com.yogesh.stylish.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(navController: NavController) {
    CenterAlignedTopAppBar(modifier = Modifier.height(70.dp),


        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface, // Surface color
            titleContentColor = MaterialTheme.colorScheme.onSurface, // Title  color (If Text Available)
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface, // Navigation icon  color
            actionIconContentColor = MaterialTheme.colorScheme.onSurface // Action icons  color
        ), title = {
            Image(painter = painterResource(id = R.drawable.img_app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.height(36.dp))
        }, navigationIcon = {
            IconButton(onClick = {}, modifier = Modifier.padding(start = 4.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "Menu",
                    modifier = Modifier.size(28.dp))
            }
        }, actions = {
            IconButton(onClick = {navController.navigate(Routes.ProfileScreen)},modifier = Modifier
                .padding(end = 4
                .dp)) {
                Image(painter = painterResource(R.drawable.img_profile_pic),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop)
            }
        })
}