package com.yogesh.stylish.presentation.theme 

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogesh.stylish.R

val Montserrat = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_extrabold, FontWeight.ExtraBold)
)

val Typography = Typography(

    // Use for: Largest headings (e.g., "Welcome Back!", "Create an Account")
    headlineLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 43.sp,
        letterSpacing = 0.sp
    ),

    // Use for: Screen titles (e.g., "Checkout", "Forgot password?")
    headlineMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),

    // Use for: Section titles (e.g., "Deal of the Day"), Profile name
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium, 
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    // Use for: Input field text, Card titles, smaller section headers
    titleMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp, 
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    // Use for: Primary Button Text ("Login", "Submit")
    labelLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp, 
        lineHeight = 24.sp, 
        letterSpacing = 0.sp
    ),

    // Use for: Main paragraph text, Product descriptions, Input labels
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp, 
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),

    // Use for: Subtitles ("OR Continue with"), Onboarding description
    bodyMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold, 
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 2.sp
    ),

    // Use for: Small captions, ratings, prices, Bottom Nav labels
    bodySmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    )

    /* --- Add other styles (labelMedium, labelSmall etc.) if needed --- */
)