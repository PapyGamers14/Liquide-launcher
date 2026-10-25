package fr.liquide.launcher.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Canonical runtime tokens; DESIGN.md records their meaning and mapping. */
object LiquideTokens {
    val Background = Color(0xFF101925)
    val Atmosphere = Color(0xFF2D4654)
    val Surface = Color(0xFF253544)
    val Foreground = Color(0xFFF5F7FA)
    val Secondary = Color(0xFFBCCAD5)
    val Focus = Color(0xFFD8EDF9)
    val Inset = 40.dp
    val Gap = 20.dp
    val Radius = 22.dp
    const val FocusDurationMillis = 150
}

@Composable
fun LiquideTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = darkColorScheme(
        primary = LiquideTokens.Focus,
        background = LiquideTokens.Background,
        surface = LiquideTokens.Surface,
        onSurface = LiquideTokens.Foreground,
        onBackground = LiquideTokens.Foreground,
        secondary = LiquideTokens.Secondary,
    ), content = content)
}
