package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: Result
)

data class Result(
    @SerializedName("Id_Usuario")
    val idUsuario: String,
    @SerializedName("Nombre_Usuario")
    val nombreUsuario: String,
    @SerializedName("Correo_Usuario")
    val correoUsuario: String,
    @SerializedName("Roll_Usuario")
    val rollUsuario: String,
    @SerializedName("Telefono_Usuario")
    val telefonoUsuario: String
)