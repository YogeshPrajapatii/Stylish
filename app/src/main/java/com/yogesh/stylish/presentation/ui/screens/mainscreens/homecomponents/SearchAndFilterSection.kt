package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun SearchAndFilterSection() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {

        var searchText by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(value = searchText, onValueChange = {
            searchText = it
        }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Search...") }, leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon ", tint = MaterialTheme.colorScheme.onSurface)
        }, trailingIcon = {
            IconButton(onClick = {}) {
                Icon(painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = "Mic Icon", tint = MaterialTheme.colorScheme.onSurface)
            }
        },
            shape = CircleShape)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("All Featured",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
              )
            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(onClick = {}, shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface 
                )) {

                Icon(painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = "Sort Icon",
                    modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sort")
            }
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(onClick = {}, shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface 
                )) {

                Icon(painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter Icon",
                    modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))

                Text("Filter")
            }
        }

    }
}
