package pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
fun PerfilScreen(
    usuarioViewModel: UsuarioViewModel,
    imagenViewModel: ImagenViewModel,
    miEmail: String, //Este parametro lo que hace es ver con que usuario estas
    onNavigate: (String) -> Unit //Este parametro es para saber que boton has apretado para llevarte a esa pantalla
) {
    val usuario by usuarioViewModel.usuarioLogueado.collectAsState(initial = null) //La lista de usuarios que devuelve la BD
    val fotosCarrusel by imagenViewModel.listaFotos.observeAsState(emptyList()) //La lista de fotos del carrusel que devuelve la BD
    val fotosPerfil by imagenViewModel.fotosPerfilUsuario.observeAsState(emptyList()) //La lista de foto del Perfil que devuelve la BD

    var personalidad by remember { mutableStateOf("") } //Es para cuando el usuario escriba en el campo de texto se guarde en la variable personalidad
    var habitos by remember { mutableStateOf("") } //Es para cuando el usuario escriba en el campo de texto se guarde en la variable habitos
    var rasgos by remember { mutableStateOf("") } //Es para cuando el usuario escriba en el campo de texto se guarde en la variable rasgos

    //Lo ejecuta esta linea si solo el email cambia
    LaunchedEffect(miEmail) {
        usuarioViewModel.getUsuarioByEmail(miEmail) //Trae la lista de personas
        imagenViewModel.cargarImagenesPorEmailYTipo(miEmail, "carrusel") //Trae la lista de fotos del carrusel (pasa por parametro el correo con el que estas y el tipo de foto que quieres)
        imagenViewModel.cargarImagenesPorEmailYTipo(miEmail, "perfil") //Trae la lista de fotos del perfil (pasa por parametro el correo con el que estas y el tipo de foto que quieres)
    }
    //Lo ejecuta solo cuando ocurra un cambio
    LaunchedEffect(usuario) {
        if (usuario != null) {
            //Esto si el usuario ha puesto los rasgos y despues vuelve a entrar con su usuario se pondra en el campo de texto los rasgos que habia puesto antes
            personalidad = usuario!!.personalidad ?: ""
            habitos = usuario!!.habitosgustos ?: ""
            rasgos = usuario!!.rasgosFisicos ?: ""
        }
    }

    Scaffold(
        bottomBar = {
            // Usamos una Column para poner el botón de guardar
            Column {
                //Surface da una pequeña elevacion (sombra) al botón
                Surface(tonalElevation = 8.dp) {
                    Button(
                        onClick = {
                            val u = usuario
                            //Verifica si el usuario no es nulo
                            if (usuario != null) {
                                //Actualiza los cuadros de texto con lo que has escrito
                                u?.personalidad = personalidad
                                u?.habitosgustos = habitos
                                u?.rasgosFisicos = rasgos
                                //Enviamos en el objeto para que se guarde en la base de datos
                                usuarioViewModel.actualizarUsuario(u)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        //El texto del boton que es para guardar los datos
                        Text(stringResource(id = R.string.save_changes))
                    }
                }
                //La barra navegacion inferior
                NavigationBar {
                    NavigationBarItem(
                        selected = false, // El icono  no esta  resaltado porque  no estamos en Inicio
                        onClick = { onNavigate("inicio") }, //Cuando el usuario pulsa, se ejecuta la función de navegación hacia la ruta "inicio".
                        label = { Text(stringResource(id = R.string.nav_home)) }, //El texto que aparece debajo del icono
                        icon = { Icon(Icons.Default.Home, null) } //El icono que va a tener el inicio
                    )
                    NavigationBarItem(
                        selected = false, // El icono  no esta  resaltado porque  no estamos en Buscar
                        onClick = { onNavigate("buscar") }, //Cuando el usuario pulsa, se ejecuta la función de navegación hacia la ruta "buscar".
                        label = { Text(stringResource(id = R.string.nav_search)) }, //El texto que aparece debajo del icono
                        icon = { Icon(Icons.Default.Search, null) }  //El icono que va a tener el buscar
                    )
                    NavigationBarItem(
                        selected = true, // El icono estara resaltado porque esta en perfil
                        onClick = { }, //Porque ya estas en perfil por eso esta vacio
                        label = { Text(stringResource(id = R.string.nav_profile)) },  //El texto que aparece debajo del icono
                        icon = { Icon(Icons.Default.Person, null) }  //El icono que va a tener el perfil
                    )
                }
            }
        }
    ) { padding ->


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Habilita el scroll vertical
                    .padding(20.dp) //Margen para que no toque los bordes
            ) {
                //Espacio para que  el contenido no choque con el boton
                Spacer(modifier = Modifier.height(40.dp))

                //Cabecera: foto de perfil y el nombre
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Busca la primera imagen disponible en la lista de fotos de perfil.
                    val urlPerfil = fotosPerfil.firstOrNull()?.url
                    //Carga la imagen del perfil
                    AsyncImage(
                        model = urlPerfil,
                        contentDescription = null,
                        modifier = Modifier
                            .size(90.dp) // Define un tamaño cuadrado
                            .clip(RoundedCornerShape(12.dp)), // Redondea las esquinas de la imagen.
                        contentScale = ContentScale.Crop // Recorta la foto para que llene el cuadro sin deformarse.
                    )
                    // Columna para poner el nombre de la mascota arriba y el dueño abajo.
                    Column(modifier = Modifier.padding(start = 15.dp)) {
                        // Muestra el nombre de la mascota
                        Text(
                            text = stringResource(id = R.string.reg_pet_name) + ": ${usuario?.nombremascota ?: "..."}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        // Muestra el nombre del dueño
                        Text(
                            text = stringResource(id = R.string.label_owner) + ": ${usuario?.nombre ?: ""} ${usuario?.apellido ?: ""}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                //Dibuja una linea para saparar contenido
                Divider(modifier = Modifier.padding(vertical = 24.dp))

                // Primer cuadro de texto para la Personalidad.
                OutlinedTextField(
                    value = personalidad,
                    onValueChange = { personalidad = it },
                    label = { Text(stringResource(id = R.string.personality)) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Segundo cuadro de texto para los Hábitos.
                OutlinedTextField(
                    value = habitos,
                    onValueChange = { habitos = it },
                    label = { Text(stringResource(id = R.string.habits)) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )
                // Tercer cuadro de texto para los Rasgos Físicos.
                OutlinedTextField(
                    value = rasgos,
                    onValueChange = { rasgos = it },
                    label = { Text(stringResource(id = R.string.traits)) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )
                // Título que dice Galería arriba de las fotos.
                Text(
                    stringResource(id = R.string.gallery),
                    style = MaterialTheme.typography.labelLarge
                )
                //Espacio entre el titulo y las fotos
                Spacer(modifier = Modifier.height(8.dp))

                // Una fila que permite hacer scroll horizontal si hay muchas fotos.
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    //Genera tantas fotos haya en la lista carrusel
                    items(fotosCarrusel.size) { index ->
                        // Tarjeta para darle forma y sombra a cada foto.
                        Card(modifier = Modifier.size(150.dp)) {
                            // Imagen de la galería.
                            AsyncImage(
                                model = fotosCarrusel[index].url, // fotosCarrusel: Es la lista completa de fotos que trajimos de la BD.
                            // 2. [index]: Es la posición  de la foto que toca poner.
                            // 3. .url: Es el atributo url que coge de la base de datos
                                contentDescription = null,
                                contentScale = ContentScale.Crop,  // Ajusta la foto al cuadrado de la Card.
                                modifier = Modifier.fillMaxSize() // La imagen ocupa toda la tarjeta.
                            )
                        }
                    }
                }
            }
        }
    }
