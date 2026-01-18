package pantallas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource // IMPORTANTE
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import es.maestre.app_mascotas.R // Importa tus recursos
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

@Composable
fun PerfilDetalleScreen(
    usuarioViewModel: UsuarioViewModel,
    imagenViewModel: ImagenViewModel,
    email: String, //Este parametro lo que hace es ver con que usuario estas
    onBack: () -> Unit //Este parametro es para saber que boton has apretado para llevarte a esa pantalla
) {
    val usuario by usuarioViewModel.usuarioLogueado.collectAsState(initial = null) //La lista de usuarios que devuelve la BD
    val fotosCarrusel by imagenViewModel.listaFotos.observeAsState(emptyList()) //La lista de fotos del carrusel que devuelve la BD
    val fotosPerfil by imagenViewModel.fotosPerfilUsuario.observeAsState(emptyList()) //La lista de foto del Perfil que devuelve la BD

    //Lo ejecuta esta linea si solo el email cambia
    LaunchedEffect(email) {
        usuarioViewModel.getUsuarioByEmail(email) //Trae la lista de personas
        imagenViewModel.cargarImagenesPorEmailYTipo(
            email,
            "carrusel"
        ) //Trae la lista de fotos del carrusel (pasa por parametro el correo con el que estas y el tipo de foto que quieres)
        imagenViewModel.cargarImagenesPorEmailYTipo(
            email,
            "perfil"
        ) //Trae la lista de fotos del perfil (pasa por parametro el correo con el que estas y el tipo de foto que quieres)
    }

    // Para poner por arriba de otras capas los elementos. Aquí se usa para poner el botón "Volver".
    Box(modifier = Modifier.fillMaxSize()) {

        //Organiza el contenido principal de arriba hacia abajo.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Permite deslizar la pantalla.
                .padding(24.dp) // Espacio para no tocar los bordes de la pantalla.
        ) {
            //Organiza la foto y los nombres.
            Row(verticalAlignment = Alignment.CenterVertically) {
                //Obtiene la URL de la foto de perfil del usuario.
                val urlPerfil = fotosPerfil.firstOrNull()?.url

                // Imagen de perfil.
                AsyncImage(
                    model = urlPerfil,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp) // Tamaño.
                        .clip(RoundedCornerShape(12.dp)) //Pone los bordes de forma redondeada.
                        // Añade un borde fino alrededor de la foto.
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant,
                            RoundedCornerShape(12.dp)
                        ),
                    contentScale = ContentScale.Crop // Recorta la imagen para que llene el cuadrado.
                )

                // Para poner los textos de al lado de la foto.
                Column(modifier = Modifier.padding(start = 20.dp)) {
                    // Texto del nombre de la mascota."
                    Text(
                        text = usuario?.nombremascota ?: stringResource(id = R.string.loading),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    // Texto del nombre del dueño y sus apellidos.
                    Text(
                        text = stringResource(id = R.string.label_owner) + ": ${usuario?.nombre ?: ""} ${usuario?.apellido ?: ""}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Color gris suave.
                    )
                }
            }

            // Línea para dividir el contenido.
            Divider(modifier = Modifier.padding(vertical = 24.dp))

            //Aqui comprueba en la base de datos si el usuario ha escrito algo y si no ha escrito nada pone un texto por defecto
            SeccionDetalleInfo(
                stringResource(id = R.string.personality),
                usuario?.personalidad ?: stringResource(id = R.string.no_data)
            )
            SeccionDetalleInfo(
                stringResource(id = R.string.habits),
                usuario?.habitosgustos ?: stringResource(id = R.string.no_data)
            )
            SeccionDetalleInfo(
                stringResource(id = R.string.traits),
                usuario?.rasgosFisicos ?: stringResource(id = R.string.no_data)
            )

            //Título para la galería de fotos.
            Text(
                stringResource(id = R.string.gallery),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            //Lista horizontal para las fotos del carrusel.
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Espacio entre cada foto.
                modifier = Modifier.height(160.dp)
            ) {
                // Recorre la lista de fotos del carrusel.
                items(fotosCarrusel) { foto ->
                    // Tarjeta para cada imagen.
                    Card(
                        modifier = Modifier.size(160.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        AsyncImage(
                            model = foto.url, // Carga la URL de la foto actual.
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            // Pone un espacio final.
            Spacer(modifier = Modifier.height(100.dp))
        }

        // Botón para volver a la pantalla anterior.
        Button(
            onClick = onBack, // Vuelve a la pantalla anterior.
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            // Icono que tiene el boton de volver.
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(id = R.string.back)) // Muestra el texto volver.
        }
    }
}


@Composable
fun SeccionDetalleInfo(titulo: String, contenido: String) {
    // Es el texto del titulo que va a tener por ejemplo: Personalidad, etc..
    Text(
        text = titulo,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    // Carta que envuelve el contenido
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        // Texto con la información  del usuario y mascota.
        Text(
            text = contenido,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
