package pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource // IMPORTANTE
import androidx.compose.ui.unit.dp
import es.maestre.app_mascotas.R // Importa tus recursos
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

@Composable
fun BuscarScreen(
    usuarioViewModel: UsuarioViewModel,
    imagenViewModel: ImagenViewModel,
    miEmail: String, //Este parametro lo que hace es ver con que usuario estas y no mostrar ese usuario
    onNavigate: (String) -> Unit //Este parametro es para saber que boton has apretado para llevarte a esa pantalla
) {
    //Es para cuando el usuario escriba en el campo de texto se guarde en la variable textoBusqueda
    var textoBusqueda by remember { mutableStateOf("") }

    val usuariosFiltrados by usuarioViewModel.usuarioList.collectAsState() //La lista de usuarios que devuelve la BD
    val fotosMuro by imagenViewModel.fotosMuro.observeAsState(emptyList()) //Lista de fotos de Supabase

    // Se ejecuta una sola vez al entrar para cargar los datos iniciales
    LaunchedEffect(Unit) {
        usuarioViewModel.getAllUsuarios() //Trae todos los usuarios de BD
        imagenViewModel.cargarImagenesMuro() // Trae las URL de las fotos del post
    }
    // Estructura principal de la pantalla
    Scaffold(
        bottomBar = {
            // Llama a la pantalla de BarraNavegacion para crear la barra inferior
            BarraNavegacionMascotas(
                rutaActual = "buscar", //Aqui pongo la seccion en la que se muestra que es la de busqueda.
                onNavigate = onNavigate
            )
        }
        //Es el espacio que va a tener la barra de navegacion
    ) { padding ->
        //Se le pone un espacio para que el texto no se pegue a los bordes
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            OutlinedTextField(
                //Muestra lo que hemos puesto en el cuadro de texto que esta guardado en la variable textoBusqueda
                value = textoBusqueda,

                onValueChange = { nuevoTexto ->
                    // Actualiza la variable con la palabra
                    textoBusqueda = nuevoTexto
                    //Lanza una consulta a la base de datos para saber que tipo de animal le has puesto en el campo de texto de antes
                    usuarioViewModel.getUsuariosByAnimal(nuevoTexto.lowercase().trim())
                },
                //Es el texto que esta arriba del campo de texto para saber que tipo de animales puedes poner
                label = { Text(stringResource(id = R.string.search_hint)) },
                // Hace que el cuadro ocupe todo el ancho disponible de la pantalla
                modifier = Modifier.fillMaxWidth(),
                //Esto es para redondear los bordes del campo texto
                shape = RoundedCornerShape(12.dp),
                // Añade el icono de la lupa al final (a la derecha) del cuadro de texto
                trailingIcon = {
                    IconButton(onClick = {
                        // Si  pulsas la lupa y el texto no está vacío, vuelve a lanzar la búsqueda
                        if (textoBusqueda.isNotBlank()) {
                            usuarioViewModel.getUsuariosByAnimal(textoBusqueda.lowercase().trim())
                        }
                    }) {
                        //Icono de la lupa
                        Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.nav_search))
                    }
                }
            )
            // Crea un espacio  de alto para que el buscador no esté pegado a la lista
            Spacer(modifier = Modifier.height(16.dp))
            //Creamos una lista con todos los usuarios menos con el usuario que ha iniciado sesion
            val resultados = usuariosFiltrados.filter { it.email != miEmail }
            //Comprobamos  si la lista esta vacia
            if (resultados.isEmpty()) {
                //Aqui se esta creando un texto en el centro de la pantalla
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    //Si la lista esta vacia pues pone un mensaje de en el box de antes
                    Text(text = stringResource(id = R.string.search_empty), color = Color.Gray)
                }
            } else {
                //Si la lista esta llena se crea una lista vertical
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp) //Deja un espacio por cada tarjeta
                ) {
                    //Aqui va poniendo cada usuario que hay en la lista de resultados
                    items(resultados) { usuario ->
                        //Aqui lo que esta haciendo es buscar la primera foto post que tenga el usuario que esta en la lista resultado y la guarda en una variable
                        val fotoPost = fotosMuro.firstOrNull { it.emailPropietario == usuario.email }
                        // Crea una tarjeta  para cada mascota
                        Card(
                            //Hace que la tarjeta ocupe todo el ancho de la pantalla
                            modifier = Modifier.fillMaxWidth(),
                            //Le pone sombreado
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            //Aqui se organiza el contenido que va a tener la tarjeta
                            Column {
                                //Mostramos si existe una foto del post de ese usuario
                                if (fotoPost != null && !fotoPost.url.isNullOrBlank()) {
                                    //Esta  funcion  muestra la imagen y el nombre del dueño
                                    PostItem(
                                        // Nombre del dueño
                                        usuario = usuario.nombre ?: stringResource(id = R.string.label_owner),
                                        // URL de la imagen de Supabase Storage
                                        imagenUrl = fotoPost.url
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}





