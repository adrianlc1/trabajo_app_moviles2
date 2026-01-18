package pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource // Necesario para los strings
import androidx.compose.ui.unit.dp
import es.maestre.app_mascotas.R // Importa tus recursos
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

@Composable
fun InicioScreen(
    usuarioViewModel: UsuarioViewModel,
    imagenViewModel: ImagenViewModel,
    miEmail: String, //Este parametro lo que hace es ver con que usuario estas y no mostrar ese usuario
    onNavigate: (String) -> Unit //Este parametro es para saber que boton has apretado para llevarte a esa pantalla
) {
    val usuarios by usuarioViewModel.usuarioList.collectAsState(initial = emptyList()) //La lista de usuarios que devuelve la BD
    val fotosPerfil by imagenViewModel.fotosPerfilGlobal.observeAsState(emptyList()) //La lista de fotosPerfil que devuelve la BD
    val fotosMuro by imagenViewModel.fotosMuro.observeAsState(emptyList()) //La lista de fotosPost que devuelve la BD

    //Aqui filtramos la lista usuario para que el usuario con el que hayas iniciado sesion no salga
    val usuariosFiltrados = remember(usuarios) {
        usuarios.filter { it.email != miEmail }
    }

    //Lo ejecuta una sola vez
    LaunchedEffect(Unit) {
        usuarioViewModel.getAllUsuarios() //Trae la lista de personas
        imagenViewModel.cargarImagenesPerfilGlobal() //Trae la lista de fotos de perfil
        imagenViewModel.cargarImagenesMuro() //Trae la lista de fotos de post
    }
    //Es la estructura para poder poner la barra de navegacion abajo
    Scaffold(
        bottomBar = {
            // Barra de navegación
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    //Esto sirve si el usuario apreta Inicio no vuelva a refrescar la pantalla
                    onClick = { /* Ya estamos en Inicio */ },
                    //Pone el nombre abajo del boton
                    label = { Text(stringResource(id = R.string.nav_home)) },
                    //El icono que va a tener el boton
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    //Esto es cuando el usuario apreta boton buscar la guarda y la utiliza en inicioActivity para saber a que activity tiene que entrar
                    onClick = { onNavigate("buscar") },
                    //Pone el nombre abajo del boton
                    label = { Text(stringResource(id = R.string.nav_search)) },
                    //El icono que va a tener el boton
                    icon = { Icon(Icons.Default.Search, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    //Esto es cuando el usuario apreta boton perfil la guarda y la utiliza en inicioActivity para saber a que activity tiene que entrar
                    onClick = { onNavigate("perfil") },
                    //Pone el nombre abajo del boton
                    label = { Text(stringResource(id = R.string.nav_profile)) },
                    //El icono que va a tener el boton
                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
                )
            }
        }
    ) { padding ->
        // Creamos una columna vertical que ocupa toda la pantalla disponible.
        Column(
            modifier = Modifier
                .fillMaxSize() // Se estira al máximo alto y ancho
                .padding(padding)  //Aplica margen para que no este pegado con los bordes de la pantalla
        ) {
            //Esto comprueba si hay usuarios en la lista
            if (usuariosFiltrados.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa todo el ancho
                        .padding(vertical = 12.dp), // Espacio arriba y abajo de la fila
                    contentPadding = PaddingValues(horizontal = 8.dp), //Margen de los lados
                    horizontalArrangement = Arrangement.spacedBy(12.dp) //Espacio entre cada usuario
                ) {
                    //Va cogiendo cada usuario que este en la lista filtrada
                    items(usuariosFiltrados) { usuario ->
                        // Busca la foto de perfil que coincida con el email del usuario
                        val foto = fotosPerfil.find { it.emailPropietario == usuario.email }
                        //Es la caja que permite hacer click para entrar en detalles del usario
                        Box(modifier = Modifier.clickable {
                            // Al tocar, navegamos a la pantalla de detalle pasando su email
                            onNavigate("detalle/${usuario.email}")
                        }) {
                            // Componente visual que hace el círculo y pone el nombre del usuario
                            UsuarioItem(
                                nombre = usuario.nombre ?: "Nombre",
                                imagenUrl = foto?.url
                            )
                        }
                    }
                }
            }
            //Una linea divisora para dividir entre usuario perfil y el post
            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            //Una lista vertica en la que deja hacer scroll
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // Se estira al máximo alto y ancho
                contentPadding = PaddingValues(bottom = 16.dp) // Espacio extra al final de la lista
            ) {
                //Recorremos los usuarios para ver quién tiene posts
                items(usuariosFiltrados) { usuario ->
                    //Miramos si el usuario tiene una foto del post.
                    val fotoPost = fotosMuro.find { it.emailPropietario == usuario.email }
                    //Si tiene una foto el usuario la crea.
                    if (fotoPost != null) {
                        PostItem(
                            usuario = usuario.nombre ?: "Nombre",
                            imagenUrl = fotoPost.url
                        )
                    }
                }
            }
        }
    }
}