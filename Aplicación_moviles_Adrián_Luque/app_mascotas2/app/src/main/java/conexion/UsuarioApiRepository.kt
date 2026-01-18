package conexion

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import model.UsuarioApi

class UsuarioApiRepository(private val client: HttpClient) {

    //Esta función descarga la lista de usuarios desde la API KEY.
    suspend fun getUsers(): List<UsuarioApi>{
        //body() lo que hace es convertir el JSON en una lista de objetos
        return client.get("https://jsonplaceholder.typicode.com/users").body()
    }
}