package com.allergyfree.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Cyan500,
    onPrimary = Color.White,
    secondary = Cyan700,
    onSecondary = Color.White,
    background = Gray50,
    onBackground = Gray900,
    surface = Color.White,
    onSurface = Gray900,
    error = Red500,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Cyan500,
    onPrimary = Color.White,
    secondary = Cyan700,
    onSecondary = Color.White,
    background = Dark900,
    onBackground = Color.White,
    surface = Dark800,
    onSurface = Color.White,
    error = Red500,
    onError = Color.White
)

@Composable
fun AllergyFreeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
