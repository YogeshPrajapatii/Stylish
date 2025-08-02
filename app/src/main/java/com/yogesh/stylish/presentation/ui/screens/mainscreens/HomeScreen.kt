package com.yogesh.stylish.presentation.ui.screens.mainscreens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(topBar = { HomeAppBar() }, bottomBar = {}) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) { }

    }
}

