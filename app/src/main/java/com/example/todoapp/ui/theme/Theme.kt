package com.example.todoapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define custom colors
val BluePrimary = Color(0xFF185ABD) // #185ABD
val BlueSecondary = Color(0xFF4191FD) // #4191FD

// Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    tertiary = Color(0xFFBB86FC) // You can change this to another custom color if desired
)

// Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    tertiary = Color(0xFF6200EE), // You can change this to another custom color if desired
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun ToDoAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    // Choose the color scheme based on the dark theme setting
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    // Apply the chosen color scheme
    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // Assuming you have a Typography setup
        content = content
    )
}
