package com.yogesh.stylish.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogesh.stylish.R

val Montserrat = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_extrabold, FontWeight.ExtraBold))


val Typography = Typography(headlineLarge = TextStyle(fontFamily = Montserrat,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 34.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.5.sp),
    headlineMedium = TextStyle(fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp),
    titleLarge = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 22.sp),
    titleMedium = TextStyle(fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp),
    bodyLarge = TextStyle(fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp))