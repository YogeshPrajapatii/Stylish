package com.yogesh.stylish.presentation.feature.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var fullName by remember { mutableStateOf(state.profileInfo?.fullName ?: "") }
    var email by remember { mutableStateOf(state.profileInfo?.email ?: "") }
    var pincode by remember { mutableStateOf(state.profileInfo?.pincode ?: "") }
    var address by remember { mutableStateOf(state.profileInfo?.address ?: "") }
    var city by remember { mutableStateOf(state.profileInfo?.city ?: "") }
    var bankAcc by remember { mutableStateOf(state.profileInfo?.bankAccountNumber ?: "") }
    var holderName by remember { mutableStateOf(state.profileInfo?.accountHolderName ?: "") }
    var ifsc by remember { mutableStateOf(state.profileInfo?.ifscCode ?: "") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(state.profileInfo?.imageUri?.let { Uri.parse(it) }) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedImageUri = uri }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.AccountCircle, null, Modifier.size(100.dp).clip(CircleShape), MaterialTheme.colorScheme.primary)
                }
                Surface(
                    modifier = Modifier.size(30.dp).clickable { galleryLauncher.launch("image/*") },
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Edit, null, Modifier.padding(6.dp), tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("Personal Details")
            ProfileTextField("Full Name", fullName) { fullName = it }
            ProfileTextField("Email Address", email) { email = it }

            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Address Details")
            ProfileTextField("Pincode", pincode) { pincode = it }
            ProfileTextField("Address", address) { address = it }
            ProfileTextField("City", city) { city = it }

            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Bank Details")
            ProfileTextField("Bank Account Number", bankAcc) { bankAcc = it }
            ProfileTextField("Account Holder Name", holderName) { holderName = it }
            ProfileTextField("IFSC Code", ifsc) { ifsc = it }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.saveProfile(selectedImageUri?.toString(), fullName, email, pincode, address, city, bankAcc, holderName, ifsc)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4B6E))
            ) {
                Text("Save Changes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(text = title, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), fontWeight = FontWeight.Bold, fontSize = 16.sp)
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium
    )
}