package pantallas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource // Para el array de animales
import androidx.compose.ui.res.stringResource      // Para textos individuales
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import es.maestre.app_mascotas.R // Asegúrate de que este import sea el de tu proyecto
import model.Usuario
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

@Composable
fun RegisterScreen(
    usuarioViewModel: UsuarioViewModel,
    imagenViewModel: ImagenViewModel,
    onRegistroClick: (Usuario, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") } // Guarda el texto de la nombre
    var apellido by remember { mutableStateOf("") } // Guarda el texto de la apellido
    var email by remember { mutableStateOf("") } // Guarda el texto de la email
    var password by remember { mutableStateOf("") } // Guarda el texto de la contraseña
    var nombreMascota by remember { mutableStateOf("") } // Guarda el texto de la nombremascota

    // Controla si el menú desplegable de animales está abierto o cerrado
    var expanded by remember { mutableStateOf(false) }

   //Carga las opciones que hay en el menu desplegable
    val opciones = stringArrayResource(id = R.array.tipos_mascotas).toList()

    // Guarda el animal que has seleccionado en el desplegable
    var tipoAnimal by remember { mutableStateOf(opciones[0]) }

    //Diseño de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espacio automático entre campos texto
    ) {
        //Para poner la imagen con el logo circular
        Image(

            painter = painterResource(id = R.drawable.logoperro),
            contentDescription = "Logo Registro",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))
        // Título de la pantalla
        Text(
            text = stringResource(id = R.string.reg_title), // "Crea tu cuenta"
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        //Los campos de texto de Nombre, Apellido, Email, Contraseña y Mascota
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(stringResource(id = R.string.reg_name)) }, // "Nombre"
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text(stringResource(id = R.string.reg_surname)) }, // "Apellido"
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.prompt_email)) }, // "Email"
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.prompt_password)) }, // "Contraseña"
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nombreMascota,
            onValueChange = { nombreMascota = it },
            label = { Text(stringResource(id = R.string.reg_pet_name)) }, // "Nombre de tu mascota"
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = tipoAnimal,
                onValueChange = { }, //No puede cambiarse escribiendo
                readOnly = true, // solo se cambia eligiendolo en el menu desplegable
                label = { Text(stringResource(id = R.string.reg_pet_type)) }, //El texto que tiene
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = { // El icono de la flecha hacia abajo
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
            //El menu que sale al apreta el icono de la flecha
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            tipoAnimal = opcion // Cambia el animal elegido
                            expanded = false  // Cierra el menú
                        }
                    )
                }
            }
        }
        //Boton de registrarse
        Button(
            onClick = {
                // Mira si el email y la contraseña no están vacíos
                if (email.isNotBlank() && password.isNotBlank()) {
                    val user = Usuario(
                        id = null,
                        nombre = nombre,
                        apellido = apellido,
                        contrasena = password,
                        email = email,
                        nombremascota = nombreMascota,
                        tipoanimal = tipoAnimal,
                        personalidad = "", // Se rellenarán más tarde en el perfil
                        habitosgustos = "", // Se rellenarán más tarde en el perfil
                        rasgosFisicos = "" // Se rellenarán más tarde en el perfil
                    )
                    //Llamamos a la funcion para registrar ese usuario
                    onRegistroClick(user, tipoAnimal)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(60.dp)
        ) {
            Text(stringResource(id = R.string.action_register)) // Este es el texto que tiene el boton
        }
    }
}
