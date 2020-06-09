package com.nda.blum.DAO
import com.google.gson.annotations.SerializedName

data class FindUserResponse (
    @SerializedName("Id_Usuario")
    val Id_Usuario: String,
    @SerializedName("Nombre_Usuario")
    val Nombre_Usuario: String,
    @SerializedName("Correo_Usuario")
    val Correo_Usuario: String,
    @SerializedName("Telefono_Usuario")
    val Telefono_Usuario: String
)