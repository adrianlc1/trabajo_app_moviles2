package es.maestre.app_mascotas

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import es.maestre.app_mascotas.ui.AppMascotasTheme

import pantallas.PerfilDetalleScreen
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

@AndroidEntryPoint
class PerfilDetalleActivity : AppCompatActivity() {

    // Se inicia  los ViewModels
    //Maneja la funcionalidad de los usuarios,las imagenes
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val imagenViewModel: ImagenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            //Recuperamos el email de la pantalla anterior
            val emailMascota = intent.getStringExtra("EMAIL_MASCOTA") ?: ""
            //Aplica los temas que tengo en la carpeta ui/AppMascotasTheme
            AppMascotasTheme {
                //Crea una superficie que ocupa toda la pantalla
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background //Color del fondo
                ) {
                    //Llamamos a la funcion que esta dentro de la carpeta pantallas/PerfilDetalle
                    PerfilDetalleScreen(
                        usuarioViewModel = usuarioViewModel,
                        imagenViewModel = imagenViewModel,
                        email = emailMascota,
                        //Aqui cuando aprete el boton vuelva a la pantalla de inicio
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}