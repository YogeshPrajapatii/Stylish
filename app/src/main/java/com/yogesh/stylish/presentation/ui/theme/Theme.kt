package com.yogesh.stylish.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// AppColorScheme wala hissa waisa hi rahega
private val AppColorScheme = lightColorScheme(
    primary = StylishRed,
    onPrimary = White,
    secondary = StylishGray,
    background = StylishBackground,
    surface = White,
    onBackground = StylishBlack,
    onSurface = StylishBlack,
)

@Composable
fun MyFirstComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = AppColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}