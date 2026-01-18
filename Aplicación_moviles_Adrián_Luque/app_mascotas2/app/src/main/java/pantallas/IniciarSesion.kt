package pantallas

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import es.maestre.app_mascotas.RegistroActivity
import viewmodel.UsuarioViewModel
import androidx.compose.ui.res.stringResource // Necesario para usar el XML
import es.maestre.app_mascotas.R



@Composable
fun LoginScreen(viewModel: UsuarioViewModel) {
    val context = LocalContext.current // Permite lanzar Toasts
    var email by remember { mutableStateOf("") } // Guarda el texto del correo
    var password by remember { mutableStateOf("") } // Guarda el texto de la contraseña

    //Guardar el mensaje de error en una variable para usarlo en el Toast (para saber si esta en ingles o en español)
    val errorCredentials = stringResource(id = R.string.login_error)

    // Es el fondo de la pantalla que se adapta al color del sistema
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        //Column organiza los elementos  de arriba a abajo, centrados en la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Para poner la imagen con el logo circular
            Image(
                painter = painterResource(id = R.drawable.logoperro),
                contentDescription = "Logo de la App",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            //Es el titulo de bienvenida
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            //Espacio de separacion
            Spacer(modifier = Modifier.height(32.dp))

            //Campo de texto para el Email
            OutlinedTextField(
                value = email, //Mustra el valor de la variable email
                onValueChange = { email = it }, // Actualiza la variable cada vez que escribes
                label = { Text(stringResource(id = R.string.prompt_email)) }, //Texto que se pone dentro del campo de texto
                modifier = Modifier.fillMaxWidth()  //Hace que la tarjeta ocupe todo el ancho de la pantalla
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre campos

            // Campo de texto para la Contraseña
            OutlinedTextField(
                value = password, //Mustra el valor de la variable contraseña
                label = { Text(stringResource(id = R.string.prompt_password)) }, //Texto que se pone dentro del campo de texto
                onValueChange = { password = it }, // Actualiza la variable cada vez que

                modifier = Modifier.fillMaxWidth() //Hace que la tarjeta ocupe todo el ancho de la pantalla
            )

            // Botón principal para iniciar sesión
            Button(
                onClick = {
                    //Validamos si los datos no estan vacios
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        // Llamamos a la funcion login para saber si el usuario existe en la base de datos
                        viewModel.login(email, password) { usuario ->
                            //Si da nulo es que el usuario no existe y te pondra el toast.
                            if (usuario == null) {
                                Toast.makeText(context, errorCredentials, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    //Ocupa todo el ancho de la pantalla
                    .fillMaxWidth()
                    //deja un espacio por encima del boton
                    .padding(top = 32.dp)
                    //Fija la altura del boton
                    .height(56.dp)
            ) {
                //El texto que tiene dentro del boton
                Text(stringResource(id = R.string.action_sign_in))
            }
            // Botón de texto para ir a la pantalla de Registro
            TextButton(
                onClick = {
                    //Abre la pantall de Registro al apretar el texto
                    context.startActivity(Intent(context, RegistroActivity::class.java))
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                // Texto "¿No tienes cuenta? Regístrate"
                Text(stringResource(id = R.string.no_account))
            }
        }
    }
}