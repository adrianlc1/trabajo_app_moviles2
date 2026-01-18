package conexion

import android.content.Context
import model.Imagen
import model.Usuario
import model.UsuarioApi
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

object ImagenesProvider {

    //La API  no tiene esos parametros, así que aquí le añadimos el apellido y el animal a mano.
    fun insertarUsuarioDeApiPersonalizado(
        userApi: UsuarioApi,
        apellido: String,
        tipoAnimal: String,
        usuarioViewModel: UsuarioViewModel
    ) {
        val nuevoUsuario = Usuario(
            nombre = userApi.name,
            apellido = apellido,
            contrasena = "123",
            email = userApi.email,
            nombremascota = userApi.username,
            tipoanimal = tipoAnimal
        )

        //Aqui insertamos el usuario
        usuarioViewModel.insert(nuevoUsuario)
    }

    // Creamos varios objetos Usuario usando los datos que vienen de la API
    fun cargarVariosUsuariosDeApi(listaApi: List<UsuarioApi>, usuarioViewModel: UsuarioViewModel) {
        if (listaApi.size >= 3) {

            // USUARIO 1
            insertarUsuarioDeApiPersonalizado(
                userApi = listaApi[0],
                apellido = "Maestre",
                tipoAnimal = "pajaro",
                usuarioViewModel = usuarioViewModel
            )

            // USUARIO 2
            insertarUsuarioDeApiPersonalizado(
                userApi = listaApi[1],
                apellido = "García",
                tipoAnimal = "gato",
                usuarioViewModel = usuarioViewModel
            )

            // USUARIO 3
            insertarUsuarioDeApiPersonalizado(
                userApi = listaApi[2],
                apellido = "Pérez",
                tipoAnimal = "perro",
                usuarioViewModel = usuarioViewModel
            )
        }
    }


    //Inserta las fotos de imagen Perfil, imagen post y imagenes del carrusel al usuario que se le ha pasado por parametro el correo
    fun insertarFotoPorEmail(viewModel: ImagenViewModel, email: String,urlPerfil:String,urlPost: String, urlCarrusel: List<String> ) {

        val imgPerfil = Imagen(url = urlPerfil, tipo = "perfil", emailPropietario = email)
        viewModel.insertarImagen(imgPerfil)


        val imgMuro = Imagen(url = urlPost, tipo = "post", emailPropietario = email)
        viewModel.insertarImagen(imgMuro)


        for (idCarrusel in urlCarrusel) {
            val imgPost = Imagen(url = idCarrusel, tipo = "carrusel", emailPropietario = email)
            viewModel.insertarImagen(imgPost)
        }
    }
}


