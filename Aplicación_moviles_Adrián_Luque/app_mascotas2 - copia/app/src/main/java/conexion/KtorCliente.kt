package conexion

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorCliente {
    val httpClient = HttpClient(Android) {
        // Sirve para que la app y el servidor se entiendan sobre qué tipo de datos envían.
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true

                isLenient = true
            })
        }
    }
}