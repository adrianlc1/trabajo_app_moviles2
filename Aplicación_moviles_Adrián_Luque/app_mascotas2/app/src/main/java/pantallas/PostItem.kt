package pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Import simplificado
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource // IMPORTANTE
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import es.maestre.app_mascotas.R // Importa tus recursos

@Composable
fun PostItem(usuario: String, imagenUrl: Any?) {
    //Esta variable guarda si pulsaste el corazón o no.
    var isLiked by remember { mutableStateOf(false) }

    //Es la tarjeta blanca que contiene todo.
    Card(
        modifier = Modifier
            .fillMaxWidth() // Que ocupe todo el ancho de la pantalla.
            .padding(horizontal = 12.dp, vertical = 8.dp), // Espacio por fuera para no chocar con otros posts.
        shape = RoundedCornerShape(16.dp), //Bordes redondos
        colors = CardDefaults.elevatedCardColors()
    ) {
        //Para poner los elementos uno debajo de otro.
        Column(modifier = Modifier.padding(12.dp)) {
            //Icono que esta al lado del nombre
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(12.dp)) // Separación entre icono y nombre.
                Text(usuario, style = MaterialTheme.typography.titleSmall) // El nombre del dueño de la mascota.
            }

            Spacer(Modifier.height(12.dp)) // Espacio antes de la foto.

            // Imagen del Post
            AsyncImage(
                //Si la foto no carga se pone una imagen por defecto para saber que da fallo
                model = imagenUrl ?: android.R.drawable.ic_menu_report_image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(300.dp), // Que todas las fotos midan lo mismo de alto.
                contentScale = ContentScale.Crop //Para que las fotos encaje bien sin que se tengan que estirar
            )

            //Boton like
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                // Al tocarlo, si era 'true' pasa a 'false' y al revés.
                IconButton(onClick = { isLiked = !isLiked }) {
                    Icon(
                        //Si es true el corazon se pone en rojo y si no es true se queda blanco con el borde negro
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.like_description),
                        tint = if (isLiked) Color.Red else LocalContentColor.current
                    )
                }
                Text(
                    // Texto que dice Me gusta o Te gusta según el botón.
                    text = if (isLiked) stringResource(id = R.string.liked) else stringResource(id = R.string.like),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}