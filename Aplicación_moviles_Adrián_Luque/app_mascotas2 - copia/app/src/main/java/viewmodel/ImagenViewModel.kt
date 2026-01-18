package viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import conexion.ImagenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Imagen
import javax.inject.Inject
@HiltViewModel
class ImagenViewModel @Inject constructor(application: Application, private val repository: ImagenRepository) : AndroidViewModel(application) {


    // Fotos del carrusel de un usuario específico
    private val _listaFotos = MutableStateFlow<List<Imagen>>(emptyList())
    val listaFotos: LiveData<List<Imagen>> = _listaFotos.asLiveData()

    // Fotos de perfil del usuario
    private val _fotosPerfilUsuario = MutableStateFlow<List<Imagen>>(emptyList())
    val fotosPerfilUsuario: LiveData<List<Imagen>> = _fotosPerfilUsuario.asLiveData()

    // Fotos que aparecen en el post
    private val _fotosMuro = MutableStateFlow<List<Imagen>>(emptyList())
    val fotosMuro: LiveData<List<Imagen>> = _fotosMuro.asLiveData()

    // Todas las fotos de perfil de todos los usuarios
    private val _fotosPerfilGlobal = MutableStateFlow<List<Imagen>>(emptyList())
    val fotosPerfilGlobal: LiveData<List<Imagen>> = _fotosPerfilGlobal.asLiveData()

    //Guarda una nueva imagen en la base de datos,
    fun insertarImagen(imagen: Imagen) {
        viewModelScope.launch {
            try {
                // Buscamos qué fotos tiene ya ese usuario de ese tipo
                val fotosExistentes = repository.getImagenesPorEmailYTipo(imagen.emailPropietario, imagen.tipo)

                // Comprobamos si la dirección (URL) de la imagen ya existe
                val yaExiste = fotosExistentes.any { it.url == imagen.url }

                if (!yaExiste) {
                    repository.insertarImagen(imagen)
                    Log.d("SUPABASE", "Imagen nueva insertada con éxito")
                } else {
                    Log.i("SUPABASE", "Esta imagen ya estaba guardada, no se repite.")
                }
            } catch (e: Exception) {
                Log.e("SUPABASE", "Error al insertar imagen: ${e.message}")
            }
        }
    }

    //Trae de la base de datos todas las fotos que tiene el tipo "post".
    fun cargarImagenesMuro() {
        viewModelScope.launch {
            val resultado = repository.getImagenes("post")
            _fotosMuro.emit(resultado)
        }
    }

    //Busca imágenes filtrando por el tipo perfil
    fun cargarImagenesPerfilGlobal() {
        viewModelScope.launch {
            val resultado = repository.getImagenes("perfil")
            _fotosPerfilGlobal.emit(resultado)
        }
    }

    //Busca imágenes filtrando por el email del dueño y por el tipo (perfil o carrusel).
    fun cargarImagenesPorEmailYTipo(email: String, tipo: String) {
        viewModelScope.launch {
            try {
                val resultado = repository.getImagenesPorEmailYTipo(email, tipo)


                when (tipo) {
                    "perfil" -> _fotosPerfilUsuario.emit(resultado)
                    "carrusel" -> _listaFotos.emit(resultado)
                }
            } catch (e: Exception) {
                Log.e("SUPABASE", "Error: ${e.message}")
            }
        }
    }
}

