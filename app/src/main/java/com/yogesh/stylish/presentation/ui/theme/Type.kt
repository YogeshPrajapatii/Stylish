package com.yogesh.stylish.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogesh.stylish.R

// 1. Define your custom font family using the files from the res/font folder.
val Montserrat = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_bold, FontWeight.Bold))

// 2. Create your app's Typography "rulebook".
val Typography = Typography(
    headlineLarge = TextStyle(fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp),
    bodyLarge = TextStyle(fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp),
    // Define other text styles from your Figma design as needed
)