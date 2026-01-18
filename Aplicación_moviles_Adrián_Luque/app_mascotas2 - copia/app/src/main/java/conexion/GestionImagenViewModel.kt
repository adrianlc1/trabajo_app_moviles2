package conexion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import model.Imagen
import javax.inject.Inject

@HiltViewModel
class GestionImagenViewModel @Inject constructor(
    //Para guardar el enlace  en la tabla de la base de datos
    private val repositorioImagen: ImagenRepository,
    private val repositorioAlmacenamiento: RepositorioAlmacenamiento
) : ViewModel() {

    //Sube la imagen a la base de datos
    fun subirNuevaFoto(email: String, tipo: String, bytes: ByteArray) {
        viewModelScope.launch {
            //Genera el nombre de la foto con el nombre el tipo y la hora
            val nombreUnico = "${tipo}_${email}_${System.currentTimeMillis()}.jpg"
            // Llamamos al Storage para que guarde la imagen y nos devuelva su dirección url.
            val urlGenerada = repositorioAlmacenamiento.subirImagen(nombreUnico, bytes)
            //Comrpueba si la url es nula
            if (urlGenerada != null) {
                //Si la url es valida crea el objeto Imagen
                val nuevaImagen = Imagen(
                    url = urlGenerada,
                    tipo = tipo,
                    emailPropietario = email
                )
                //Guardamos este objeto a la base de datos
                repositorioImagen.insertarImagen(nuevaImagen)
            }
        }
    }
}