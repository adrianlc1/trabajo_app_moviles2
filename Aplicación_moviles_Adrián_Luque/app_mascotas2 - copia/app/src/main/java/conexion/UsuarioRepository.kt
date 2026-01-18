package conexion

import android.util.Log
import io.github.jan.supabase.postgrest.Postgrest
import model.Usuario
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor(
    private val postgrest: Postgrest //conexion a Supabase
) {
    // Nombre de la tabla en tu base de datos
    private val tabla = "usuario"

    // Obtener todos los usuarios registrados.
    suspend fun getAllUsuarios(): List<Usuario> {
        return postgrest[tabla].select().decodeList<Usuario>()
    }

    //Mete un nuevo usuario en la base de datos.
    suspend fun insert(usuario: Usuario) {
        postgrest[tabla].insert(usuario)
    }

    // Actualizar perfil
    suspend fun update(usuario: Usuario?) {

        if (usuario != null) {

            postgrest[tabla].update(usuario) {
                filter {
                    eq("email", usuario.email)
                }
            }
        }
    }

    // Login (Busca un usuario con ese email y contraseña)
    suspend fun login(email: String, contrasena: String): Usuario? {
        return postgrest[tabla].select {
            filter {
                eq("email", email)
                eq("contrasena", contrasena)
            }
        }.decodeSingleOrNull<Usuario>()
    }

    // Buscar posts por tipo de animal
    suspend fun getPostsByAnimal(tipo: String): List<Usuario> {
        return postgrest[tabla].select {
            filter { eq("tipoanimal", tipo) }
        }.decodeList<Usuario>()
    }

    // Obtener un usuario por el email
    suspend fun getUsuarioByEmail(email: String): Usuario? {
        return postgrest[tabla].select {
            filter { eq("email", email) }
        }.decodeSingleOrNull<Usuario>()
    }

    //Cambia los datos de un usuario buscando por su email.
    suspend fun getUsuariosByAnimal(tipo: String): List<Usuario> {
        return try {
            postgrest[tabla].select {
                filter {

                    eq("tipoanimal", tipo)
                }
            }.decodeList<Usuario>()
        } catch (e: Exception) {
            Log.e("POSTGRES", "Error al filtrar por animal: ${e.message}")
            emptyList()
        }
    }
}