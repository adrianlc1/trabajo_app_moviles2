package conexion

import io.github.jan.supabase.postgrest.Postgrest
import model.Imagen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagenRepository @Inject constructor(
    private val postgrest: Postgrest //Esta el conexion a Supabase
) {
    //Es el nombre de la tabla que tiene la base de datos
    private val tabla = "imagenes"

    //insertar imagenes
    suspend fun insertarImagen(imagen: Imagen) {
        postgrest[tabla].insert(imagen)
    }

    //obtener imagenes por el tipo de animal
    suspend fun getImagenes(tipo: String): List<Imagen> {
        return postgrest[tabla].select {
            filter {
                eq("tipo", tipo)
            }
        }.decodeList<Imagen>()
    }

    // obtener imagenes por el email del usuario y por el tipo de animal
    suspend fun getImagenesPorEmailYTipo(email: String?, tipo: String): List<Imagen> {
        val emailSeguro = email ?: ""
        return postgrest[tabla].select {
            filter {
                eq("email_propietario", emailSeguro)
                eq("tipo", tipo)
            }
        }.decodeList<Imagen>()
    }
}