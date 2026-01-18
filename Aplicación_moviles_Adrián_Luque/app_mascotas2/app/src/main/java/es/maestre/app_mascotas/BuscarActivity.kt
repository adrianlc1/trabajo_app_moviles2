package es.maestre.app_mascotas

import android.content.Intent
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
import pantallas.BuscarScreen
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

@AndroidEntryPoint
class BuscarActivity : AppCompatActivity() {

    // Se inicia  los ViewModels
    //Maneja la funcionalidad de los usuarios y las imagenes
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val imagenViewModel: ImagenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperamos el email que nos pasó la pantalla anterior a través del Intent
        // Si no existe, ponemos un texto vacío
        val miEmail = intent.getStringExtra("EMAIL_LOGUEADO") ?: ""


        setContent {
            //Aplica los temas que tengo en la carpeta ui/AppMascotasTheme
            AppMascotasTheme {
                //Crea una superficie que ocupa toda la pantalla
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background //Color del fondo
                ) {
                    //Llamamos a la funcion que esta dentro de la carpeta pantallas/Buscar
                    BuscarScreen(
                        usuarioViewModel = usuarioViewModel, //Le pasamos la funcionalidad de Usuario
                        imagenViewModel = imagenViewModel, // Le pasamos la funcionalidad de Imagenes
                        miEmail = miEmail, //Le pasamos el correo para saber que usuario es
                        onNavigate = { ruta -> //Es para saber que boton a pulsado el usuario (lo puedes ver en pantallas/BarraNavegacion)

                            //Si el usuario a pulsado el boton inicio cambia de pantalla a inicio
                            if (ruta == "inicio") {

                                val intent = Intent(this, InicioActivity::class.java)
                                intent.putExtra("EMAIL_LOGUEADO", miEmail) // Le pasamos el email para la pantalla  de inicio (para saber que usuario es y poner su informacion)
                                startActivity(intent)
                            }

                            //Si el usuario a pulsado el boton perfil cambia de pantalla a perfil
                            if (ruta == "perfil") {

                                val intent = Intent(this, PerfilActivity::class.java)
                                intent.putExtra("EMAIL_LOGUEADO", miEmail) // Le pasamos el email para la pantalla  de perfil (para saber que usuario es y poner su informacion)
                                startActivity(intent)
                            }
                        }
                    )
                }
            }
        }
    }
}






