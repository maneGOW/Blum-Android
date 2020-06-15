package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class BuscarAlumnosCoachResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: List<BuscarAlumnosCoachResult>
)

data class BuscarAlumnosCoachResult(
    @SerializedName("Id_Usuario")
    val Id_Usuario: String,
    @SerializedName("Nombre_Usuario")
    val Nombre_Usuario: String,
    @SerializedName("Correo_Usuario")
    val Correo_Usuario: String,
    @SerializedName("Id_Coach")
    val Id_Coach: String,
    @SerializedName("Foto_Usuario")
    val Foto_Usuario: String
)