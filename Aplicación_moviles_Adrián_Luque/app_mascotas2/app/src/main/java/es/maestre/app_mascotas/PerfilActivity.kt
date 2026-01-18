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
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.AndroidEntryPoint
import es.maestre.app_mascotas.ui.AppMascotasTheme
import pantallas.PerfilScreen
import servicios.NightModeService
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

@AndroidEntryPoint
class PerfilActivity : AppCompatActivity() {

    // Se inicia  los ViewModels
    //Maneja la funcionalidad de los usuarios,las imagenes
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val imagenViewModel: ImagenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            //Recogemos el email del usuario que viene de la pantalla anterior.
            val emailLogueado = intent.getStringExtra("EMAIL_LOGUEADO") ?: ""

            //Aplica los temas que tengo en la carpeta ui/AppMascotasTheme
            AppMascotasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Llamamos a la funcion que esta dentro de la carpeta pantallas/Perfil
                    PerfilScreen(
                        usuarioViewModel = usuarioViewModel,
                        imagenViewModel = imagenViewModel,
                        miEmail = emailLogueado,
                        //Si el usuario pulsa el boton de salir le llevara a pantalla de login

                        onNavigate = { ruta -> // Es para saber qué botón ha pulsado el usuario (lo puedes ver en pantallas/BarraNavegacion)


                            //Si el usuario a pulsado el boton buscar cambia de pantalla a buscar
                            if (ruta == "buscar") {
                                val intent = Intent(this, BuscarActivity::class.java)

                                intent.putExtra("EMAIL_LOGUEADO", emailLogueado) // Le pasamos el email para saber quién es el usuario a la proxima pantalla
                                startActivity(intent)

                                finish()
                            }

                            //Si el usuario a pulsado el boton inicio cambia de pantalla a inicio
                            if (ruta == "inicio") {
                                val intent = Intent(this, InicioActivity::class.java)

                                intent.putExtra("EMAIL_LOGUEADO", emailLogueado) // Le pasamos el email para saber quién es el usuario a la proxima pantalla
                                startActivity(intent)

                                finish()
                            }
                        }
                    )
                }
            }
        }
    }
}


















