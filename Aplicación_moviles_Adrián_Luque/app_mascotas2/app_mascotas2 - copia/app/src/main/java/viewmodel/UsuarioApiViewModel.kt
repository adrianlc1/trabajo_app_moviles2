package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import conexion.KtorCliente
import conexion.UsuarioApiRepository
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.UsuarioApi

class UsuarioApiViewModel(application: Application) : AndroidViewModel(application) {

    //Guardamos la lista de usuarios que bajamos de la API.
    val _users = MutableStateFlow<List<UsuarioApi>>(emptyList())
    //Le pasamos el cliente para que pueda hacer las peticiones
    private val repository: UsuarioApiRepository = UsuarioApiRepository(KtorCliente.httpClient)

    init {
        loadUsers()
    }
    //Función para descargar los usuarios.
    private fun loadUsers() {
        viewModelScope.launch {
            try {
                // Le pedimos al repositorio que nos traiga los usuarios.
                val result = repository.getUsers()
                //guardamos el resultado en la lista.
                _users.value = result
                // Imprimimos un mensaje en la consola para saber que los datos llegaron.
                println("API_DEBUG: Datos recibidos: ${result.size}")
            } catch (e: Exception) {
                println("API_DEBUG: Error: ${e.message}")
                e.printStackTrace()
            }
            }
        }
    }
