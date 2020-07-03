package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class BuscarNidoCoachResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: List<BuscarNidoCoachResult>
)

data class BuscarNidoCoachResult(
    @SerializedName("Nombre_Nido")
    val Nombre_Nido: String,
    @SerializedName("id_Nido")
    val id_Nido: String,
    @SerializedName("Foto_Nido")
    val fotoNido: String
)