package pantallas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UsuarioItem(nombre: String, imagenUrl: Any?) {
    Column(
        modifier = Modifier
            .width(85.dp) //Ancho para que todos los usuarios ocupen el mismo espacio
            .padding(8.dp), // Margen para que no se peguen unos a otros
        horizontalAlignment = Alignment.CenterHorizontally // Centra todo el contenido
    ) {
        //Foto circular
        AsyncImage(
            //Si no hay foto ponemos una foto por defecto
            model = imagenUrl ?: android.R.drawable.ic_menu_gallery,
            contentDescription = "Foto de $nombre",
            modifier = Modifier
                .size(64.dp) // Tamaño del círculo
                .clip(CircleShape) //Lo pone de forma circular
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop // Recorta la foto para que llene el círculo sin estirarse
        )
        //Pone un texto debajo de la foto que es el nombre del dueño
        Text(
            text = nombre,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

