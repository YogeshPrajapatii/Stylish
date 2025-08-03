package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R

@Composable
fun HomeAppBar() {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(onClick = {}) {

            Icon(painter = painterResource(id = R.drawable.home_menu),
                contentDescription = "Home " + "Menu",
                modifier = Modifier.size(24.dp))

        }

        Image(painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.height(30.dp))

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = {}) {

            Icon(painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Profile Logo",
                modifier = Modifier.size(36.dp))

        }

    }


}
