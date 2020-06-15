package com.nda.blum.DAO
import com.google.gson.annotations.SerializedName

data class FindUser(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: FindUserResponse
)

data class FindUserResponse (
    @SerializedName("Id_Usuario")
    val Id_Usuario: String,
    @SerializedName("Nombre_Usuario")
    val Nombre_Usuario: String,
    @SerializedName("Correo_Usuario")
    val Correo_Usuario: String,
    @SerializedName("Telefono_Usuario")
    val Telefono_Usuario: String,
    @SerializedName("Foto_Usuario")
    var Foto_Usuario: String
)
