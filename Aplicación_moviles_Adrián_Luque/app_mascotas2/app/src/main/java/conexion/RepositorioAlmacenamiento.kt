package conexion

import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositorioAlmacenamiento @Inject constructor(
    private val storage: Storage //Conexion a Supabase
) {
    //Es el nombre que tiene bucket dentro de Supabase
    private val BUCKET_FOTOS = "fotos_mascotas"

    //Envia los bytes de la imagen a el Supabase
    suspend fun subirImagen(nombreArchivo: String, bytes: ByteArray): String? {
        return withContext(Dispatchers.IO) {
            try {
                val bucket = storage.from(BUCKET_FOTOS)


                bucket.upload(nombreArchivo, bytes) {
                    upsert = true
                }


                bucket.publicUrl(nombreArchivo)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    //Elimina el archivo.
    suspend fun borrarImagen(nombreArchivo: String) {
        withContext(Dispatchers.IO) {
            try {
                storage.from(BUCKET_FOTOS).delete(nombreArchivo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}