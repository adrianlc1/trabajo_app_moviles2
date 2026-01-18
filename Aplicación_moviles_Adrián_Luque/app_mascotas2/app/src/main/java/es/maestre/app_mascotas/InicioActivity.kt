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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import conexion.ImagenesProvider
import dagger.hilt.android.AndroidEntryPoint
import es.maestre.app_mascotas.ui.AppMascotasTheme
import pantallas.InicioScreen
import servicios.NightModeService
import viewmodel.ImagenViewModel
import viewmodel.UsuarioApiViewModel
import viewmodel.UsuarioViewModel

@AndroidEntryPoint
class InicioActivity : AppCompatActivity() {

    // Se inicia  los ViewModels
    //Maneja la funcionalidad de los usuarios,las imagenes y los usuario de la api key
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val imagenViewModel: ImagenViewModel by viewModels()
    private val userViewModelApi: UsuarioApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperamos el email que nos pasó la pantalla anterior a través del Intent
        val miEmail = intent.getStringExtra("EMAIL_LOGUEADO") ?: ""

        setContent {
            // Almacena los datos de la api en una lista vacia
            val listaInternet by userViewModelApi._users.collectAsState(initial = emptyList())

            //Si el contenido de la lista cambia lo vuelve a ejecutar
            LaunchedEffect(listaInternet) {
                //Miramos si la lista esta vacia
                if (listaInternet.isNotEmpty()) {
                    // Insertamos los usuarios de la API a la base de datos
                    ImagenesProvider.cargarVariosUsuariosDeApi(
                        listaApi = listaInternet,
                        usuarioViewModel = usuarioViewModel
                    )
                }
            }
            //Aplica los temas que tengo en la carpeta ui/AppMascotasTheme
            AppMascotasTheme {
                //Crea una superficie que ocupa toda la pantalla
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background //Color del fondo
                ) {
                    //Llamamos a la funcion que esta dentro de la carpeta pantallas/Inicio
                    InicioScreen(
                        usuarioViewModel = usuarioViewModel,
                        imagenViewModel = imagenViewModel,
                        miEmail = miEmail,
                        onNavigate = { ruta -> // Es para saber qué botón ha pulsado el usuario (lo puedes ver en pantallas/BarraNavegacion)


                            // Ir a la pantalla de búsqueda
                            if (ruta == "buscar") {
                                val intent = Intent(this, BuscarActivity::class.java)
                                // Pasamos nuestro email para que la pantalla de buscar cargue los datos
                                intent.putExtra("EMAIL_LOGUEADO", miEmail)
                                startActivity(intent)
                            }

                            //Ir a el perfil
                            if (ruta == "perfil") {
                                val intent = Intent(this, PerfilActivity::class.java)
                                // Pasamos nuestro email para que la pantalla de perfil cargue los datos
                                intent.putExtra("EMAIL_LOGUEADO", miEmail)
                                startActivity(intent)
                            }
                            // Aquí comprobamos si la ruta empieza por "detalle/"
                            if (ruta.startsWith("detalle/")) {

                                val emailDeLaMascota = ruta.replace("detalle/", "")

                                val intent = Intent(this, PerfilDetalleActivity::class.java)

                                intent.putExtra("EMAIL_MASCOTA", emailDeLaMascota)
                                startActivity(intent)
                            }
                        }
                    )
                }
            }
        }
    }
}
