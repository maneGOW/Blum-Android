package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class UsuariosNidoResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: List<UsuariosNidoResult>
)

data class UsuariosNidoResult(
    @SerializedName("Nombre_Usuario")
    val nombreUsuarios: String
)