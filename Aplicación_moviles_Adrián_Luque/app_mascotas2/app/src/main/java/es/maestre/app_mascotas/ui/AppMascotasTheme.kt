package es.maestre.app_mascotas.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    background = Color.Black,      // El fondo de la pantalla es negro
    surface = Color.Black,         // Las tarjetas o superficies también serán negras
    primary = Color(0xFF4CAF50),   // El color principal es un verde
    onBackground = Color.White     // El texto sobre el fondo negro será blanco
)

private val LightColors = lightColorScheme(
    background = Color.White,      // El fondo de la pantalla  es blanco
    surface = Color.White,         // Las superficies también serán blancas
    primary = Color(0xFF4CAF50),   // Mantenemos el mismo verde
    onBackground = Color.Black     // El texto sobre el fondo blanco será negro
)

//Esta funcion es para aplicar los colores
@Composable
fun AppMascotasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

