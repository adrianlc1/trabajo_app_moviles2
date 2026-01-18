package pantallas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource // IMPORTANTE
import es.maestre.app_mascotas.R // Importa tus recursos

@Composable
fun BarraNavegacionMascotas(rutaActual: String, onNavigate: (String) -> Unit) {
    //Contenedor principal de la barra inferior
    NavigationBar {
        NavigationBarItem(
            // Se marca como seleccionado si la ruta coincide con "inicio"
            selected = rutaActual == "inicio",
            onClick = { onNavigate("inicio") },
            label = { Text(stringResource(id = R.string.nav_home)) },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )
        NavigationBarItem(
            // Se marca como seleccionado si la ruta coincide con "buscar"
            selected = rutaActual == "buscar",
            onClick = { onNavigate("buscar") },
            label = { Text(stringResource(id = R.string.nav_search)) },
            icon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) }
        )
        NavigationBarItem(
            // Se marca como seleccionado si la ruta coincide con "perfil"
            selected = rutaActual == "perfil",
            onClick = { onNavigate("perfil") },
            label = { Text(stringResource(id = R.string.nav_profile)) },
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) }
        )
    }
}