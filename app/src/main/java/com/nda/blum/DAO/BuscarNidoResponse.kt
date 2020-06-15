package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class BuscarNidoResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: BuscarNidoResult
)

data class BuscarNidoResult(
    @SerializedName("Foto_Nido")
    val Foto_Nido: String,
    @SerializedName("Nombre_Nido")
    val Nombre_Nido: String
)
