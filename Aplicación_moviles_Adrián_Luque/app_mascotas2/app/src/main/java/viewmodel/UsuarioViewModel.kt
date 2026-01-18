package viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import conexion.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Usuario
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val repository: UsuarioRepository // El repositorio que conecta con la base de datos
) : ViewModel() {

    // Lista completa de todos los usuarios
    private val _usuarioList = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarioList: StateFlow<List<Usuario>> = _usuarioList.asStateFlow()

    // Datos del usuario que tiene la sesión abierta
    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null)
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado.asStateFlow()

    // Lista para guardar resultados cuando buscamos o filtramos mascotas
    private val _usuariosFiltrados = MutableStateFlow<List<Usuario>>(emptyList())
    val usuariosFiltrados: StateFlow<List<Usuario>> = _usuariosFiltrados.asStateFlow()

    //Trae todos los usuarios registrados en la base de datos.
    fun getAllUsuarios() {
        viewModelScope.launch {
            try {
                val lista = repository.getAllUsuarios()
                _usuarioList.emit(lista)
                Log.d("POSTGRES", "Usuarios cargados: ${lista.size}")
            } catch (e: Exception) {
                Log.e("POSTGRES", "Error al obtener usuarios: ${e.message}")
            }
        }
    }
    //Comprueba si el email y la contraseña son correctos.
    fun login(email: String, contrasena: String, onResult: (Usuario?) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val usuario = repository.login(email, contrasena)
                _usuarioLogueado.emit(usuario)
                onResult(usuario)
            } catch (e: Exception) {
                Log.e("POSTGRES", "Error en login: ${e.message}")
                onResult(null)
            }
        }
    }

    //Registra un nuevo usuario en la base de datos.
    fun insert(usuario: Usuario) {
        viewModelScope.launch {
            try {
                repository.insert(usuario)
                _usuarioLogueado.emit(usuario)
                Log.d("POSTGRES", "Usuario creado con éxito: ${usuario.email}")
            } catch (e: Exception) {
                Log.e("POSTGRES", "ERROR CRÍTICO AL CREAR USUARIO: ${e.message}")
            }
        }
    }

    //Filtra los usuarios según el tipo de animal
    fun getUsuariosByAnimal(tipo: String) {
        viewModelScope.launch {
            val lista = if (tipo.isEmpty()) {
                repository.getAllUsuarios()
            } else {
                repository.getUsuariosByAnimal(tipo)
            }
            _usuarioList.value = lista
        }
    }

    //Busca los datos de un usuario concreto usando su email.
    fun getUsuarioByEmail(email: String) {
        viewModelScope.launch {
            try {
                val usuario = repository.getUsuarioByEmail(email)
                _usuarioLogueado.emit(usuario)
            } catch (e: Exception) {
                Log.e("POSTGRES", "Error: ${e.message}")
            }
        }
    }

        //Actualiza la información del perfil
        fun actualizarUsuario(usuario: Usuario?) {
            viewModelScope.launch {
                try {

                    repository.update(usuario)


                    _usuarioLogueado.value = usuario

                    Log.d("SQLITE", "Usuario actualizado: ${usuario?.rasgosFisicos}")
                } catch (e: Exception) {
                    Log.e("SQLITE", "Error al actualizar: ${e.message}")
                }
            }
        }
        fun cerrarSesion() {
        _usuarioLogueado.value = null
        }
    }

