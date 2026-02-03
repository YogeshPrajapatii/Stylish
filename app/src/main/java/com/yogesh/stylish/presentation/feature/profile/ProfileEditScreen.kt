package com.yogesh.stylish.presentation.feature.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import com.yogesh.stylish.presentation.component.StylishButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    var fullName by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var bankAcc by remember { mutableStateOf("") }
    var holderName by remember { mutableStateOf("") }
    var ifsc by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(state.profileInfo) {
        state.profileInfo?.let {
            fullName = it.fullName
            pincode = it.pincode
            address = it.address
            city = it.city
            bankAcc = it.bankAccountNumber
            holderName = it.accountHolderName
            ifsc = it.ifscCode
            selectedImageUri = it.imageUri?.let { uri -> Uri.parse(uri) }
        }
    }

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
        },
        bottomBar = {
            Surface(tonalElevation = 8.dp) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    StylishButton(
                        text = "Save Changes",
                        onClick = {
                            viewModel.saveProfile(
                                selectedImageUri?.toString(),
                                fullName,
                                pincode,
                                address,
                                city,
                                bankAcc,
                                holderName,
                                ifsc
                            ) {
                                navController.popBackStack()
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Surface(
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = (-4).dp, y = (-4).dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = { galleryLauncher.launch("image/*") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.padding(6.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            SectionHeader("Personal Details")
            ProfileEntryField("Full Name", fullName, state.validationErrors["name"]) { fullName = it }

            Spacer(modifier = Modifier.height(16.dp))

            SectionHeader("Address Details")
            ProfileEntryField("Pincode", pincode, state.validationErrors["pincode"]) { if (it.length <= 6) pincode = it }
            ProfileEntryField("Full Address", address, state.validationErrors["address"]) { address = it }
            ProfileEntryField("City", city, state.validationErrors["city"]) { city = it }

            Spacer(modifier = Modifier.height(16.dp))

            SectionHeader("Bank Details")
            ProfileEntryField("Bank Account Number", bankAcc, state.validationErrors["bank"]) { bankAcc = it }
            ProfileEntryField("Account Holder Name", holderName, state.validationErrors["holder"]) { holderName = it }
            ProfileEntryField("IFSC Code", ifsc, state.validationErrors["ifsc"]) { if (it.length <= 11) ifsc = it.uppercase() }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun ProfileEntryField(
    label: String,
    value: String,
    errorMessage: String?,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            isError = errorMessage != null,
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}