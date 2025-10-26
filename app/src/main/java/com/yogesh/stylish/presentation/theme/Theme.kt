package com.yogesh.stylish.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
    lightColorScheme(primary = PrimaryRed,    // Main brand color (buttons, links)
        onPrimary = TextWhite,                // Color on top of primary (e.g., button text)
        background = BackgroundWhite,         // Default screen background
        onBackground = TextPrimary,           // Color on top of background (main text)
        surface = SurfaceWhite,               // Color for elevated surfaces (cards, fields)
        onSurface = TextPrimary,              // Color on top of surface (text in cards)
        onSurfaceVariant = TextSecondaryGray, // Secondary text color on surfaces
        error = ErrorRed                      // Color for errors
    )

private val DarkColorScheme =
    darkColorScheme(primary = PrimaryRed,          // Main brand color remains the same
        onPrimary = TextWhite,                     // Color on top of primary remains the same
        background = Color(0xFF000000),     // Dark background for dark theme
        onBackground = TextWhite,                  // Light text on dark background
        surface = Color(0xFF1E1E1E),        // A common dark surface color (slightly lighter than // black)
        onSurface = TextWhite,                     // Light text on dark surfaces
        onSurfaceVariant = TextSecondaryGray,      // Use StylishGray or TextSecondaryGray for secondary dark text
        error = ErrorRed                           // Error color remains the same
        
    )

@Composable
fun StylishTheme( 
    darkTheme: Boolean = isSystemInDarkTheme(), 
    content: @Composable () -> Unit 
) {
    
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

   
    MaterialTheme(colorScheme = colorScheme,
        typography = Typography, 
        content = content        
    )
}