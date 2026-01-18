package es.maestre.app_mascotas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import conexion.ImagenesProvider
import dagger.hilt.android.AndroidEntryPoint
import es.maestre.app_mascotas.ui.AppMascotasTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import model.Usuario
import pantallas.LoginScreen
import pantallas.RegisterScreen
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

@AndroidEntryPoint
class RegistroActivity : AppCompatActivity() {

    // Se inicia  los ViewModels
    //Maneja la funcionalidad de los usuarios,las imagenes
    private val viewModel: UsuarioViewModel by viewModels()
    private val imagenViewModel: ImagenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //Esto lo ejecuta solo una vez
            LaunchedEffect(Unit) {
                //  se queda esperando a cualquier cambio que ocurra en el registro.
                viewModel.usuarioLogueado.collectLatest { usuario ->
                    if (usuario != null) {
                        // Si el usuario no es nulo, mostramos un aviso y saltamos al Login
                        Toast.makeText(baseContext, "¡Mascota registrada!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(baseContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            //Aplica los temas que tengo en la carpeta ui/AppMascotasTheme
            AppMascotasTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background // el color del fondo
                ) {
                    //Llamamos a la funcion que esta dentro de la carpeta pantallas/Registro
                    RegisterScreen(
                        usuarioViewModel = viewModel,
                        imagenViewModel = imagenViewModel,
                        //Cuando apretas el boton registrar
                        onRegistroClick = { usuario, tipoAnimal ->
                            registrarTodo(usuario, tipoAnimal)
                        }
                    )
                }
            }
        }
    }

    private fun registrarTodo(usuario: Usuario, tipoAnimal: String) {
        lifecycleScope.launch {
            try {
                // 1. Guardamos el usuario
                viewModel.insert(usuario)

                // Guardamos la imagen
                subirFotosSegunAnimal(tipoAnimal, usuario.email)

            } catch (e: Exception) {
                Log.e("POSTGRES", "Error: ${e.message}")
            }
        }
    }

    /**
     * Asigna fotos por defecto para que el perfil no aparezca vacío al crearse.
     */
    private fun subirFotosSegunAnimal(tipo: String, email: String) {
        //El imagenesProvider lo guardo en una variable para utilizarla despues
        val provider = ImagenesProvider
        //El triple coge el primer url y lo guarda en el parametro fotoPerfil y asi sucesivamente
        val (fotoPerfil, fotoPortada, carrusel) = when {
            //Elige el tipo de animal y guardara unas imagenes en la base de datos
            tipo.contains("Gato", true) -> Triple(
                "https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/perfilgato.jpg",
                "https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/images%20(3)%20(1).jpg",
                listOf("https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/carruselgato.jpg")
            )
            tipo.contains("Perro", true) -> Triple(
                "https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/perfilperro.jpg",
                "https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/postperro.jpg",
                listOf("https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/carrruselperro.jpg")
            )
            else -> Triple(
                "https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/perfilpajaro.jpg",
                "https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/postpajaro.jpg",
                listOf("https://ipczrmxewgqrgaffjdly.supabase.co/storage/v1/object/public/fotos_mascotas/carruselpajaro.jpg")
            )
        }
        //Guarda las fotos en la base de datos con el correo correspondiente
        provider.insertarFotoPorEmail(imagenViewModel, email, fotoPerfil, fotoPortada, carrusel)
    }
}
