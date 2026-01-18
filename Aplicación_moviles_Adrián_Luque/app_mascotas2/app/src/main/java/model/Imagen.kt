package model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName



@Serializable
data class Imagen(
    @SerialName("id")
    val id: String? = null,

    @SerialName("url")
    val url: String,

    @SerialName("tipo")
    val tipo: String,

    @SerialName("email_propietario")
    val emailPropietario: String
)