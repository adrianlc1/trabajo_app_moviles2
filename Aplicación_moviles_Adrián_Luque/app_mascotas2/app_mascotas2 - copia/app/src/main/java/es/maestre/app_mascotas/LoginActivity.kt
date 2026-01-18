package es.maestre.app_mascotas

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import es.maestre.app_mascotas.ui.AppMascotasTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pantallas.LoginScreen
import pantallas.RegisterScreen
import servicios.NightModeService
import viewmodel.UsuarioViewModel

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    // Se inicia  los ViewModels
    //Maneja la funcionalidad de los usuarios,las imagenes y los usuario de la api key
    private val viewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicia el servicio de modo noche
        val serviceIntent = Intent(this, NightModeService::class.java)
        startService(serviceIntent)

        setContent {

            //  se queda esperando a cualquier cambio que ocurra en el  login.
            viewModel.usuarioLogueado.asLiveData().observe(this) { usuario ->
                // Comprueba si el usuario esta vacio.
                if (usuario != null) {
                    //Cambia de pantalla a inicio
                    val intent = Intent(this, InicioActivity::class.java)
                    //Le pasamos el email para la pantalla  de inicio
                    intent.putExtra("EMAIL_LOGUEADO", usuario.email)
                    startActivity(intent)
                    finish()
                }
            }


                //Aplica los temas que tengo en la carpeta ui/AppMascotasTheme
                AppMascotasTheme(
                ) {
                    //Llamamos a la funcion que esta dentro de la carpeta pantallas/IniciarSesion
                    LoginScreen(viewModel = viewModel)
                }
            }

        }
    }


