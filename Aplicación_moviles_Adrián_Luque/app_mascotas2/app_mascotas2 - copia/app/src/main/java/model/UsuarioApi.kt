package model

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioApi(
    val name: String,
    val email: String,
    val username: String
)