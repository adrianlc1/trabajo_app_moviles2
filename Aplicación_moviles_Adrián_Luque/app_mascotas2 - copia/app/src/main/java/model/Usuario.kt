package model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Usuario(
    @SerialName("id_usuario")
    val id: String? = null,

    @SerialName("nombre")
    var nombre: String,

    @SerialName("apellido")
    var apellido: String,

    @SerialName("contrasena")
    var contrasena: String,

    @SerialName("email")
    var email: String,

    @SerialName("nombremascota")
    var nombremascota: String,

    @SerialName("tipoanimal")
    var tipoanimal: String,

    @SerialName("personalidad")
    var personalidad: String? = null,

    @SerialName("habitosgustos")
    var habitosgustos: String? = null,

    @SerialName("rasgosFisicos")
    var rasgosFisicos: String? = null
)