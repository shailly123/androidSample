package com.example.takehome.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


/**
 * Custom colors and typography for reference , currently only using default colors
 */
private val CustomLightColors = lightColors(
    primary = Color(0xFF6200EE),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),

)
private val CustomDarkColors = darkColors(
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),

)
private val CustomTypography = Typography(
    body1 = TextStyle(
        color = Color.Black,
        fontSize = 16.sp
    ),
)

@Composable
fun TakeHomeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) CustomDarkColors else CustomLightColors

    MaterialTheme(
        colors = MaterialTheme.colors,
        typography = MaterialTheme.typography,
        content = content
    )
}