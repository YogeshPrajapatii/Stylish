package com.yogesh.stylish.presentation.ui.screens.mainscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun SearchAndFilterSection() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {

        var searchText by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(value = searchText, onValueChange = {
            searchText = it
        }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Search...") }, leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon ")
        }, trailingIcon = {
            IconButton(onClick = {}) {
                Icon(painter = painterResource(id = R.drawable.mic_ic),
                    contentDescription = "Mic Icon")
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text("All Featured", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            OutlinedButton(onClick = {}) {

                Icon(painter = painterResource(id = R.drawable.sort_ic),
                    contentDescription = "Sort Icon",
                    modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sort")
            }
            
            OutlinedButton(onClick = {}) {
                
                Icon(painter = painterResource(id = R.drawable.filter_ic),
                    contentDescription = "Filter Icon",
                    modifier = Modifier.size(18.dp))
                Text("Filter")
            }
        }


    }

}