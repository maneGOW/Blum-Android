package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class AsignarCoachResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: ResultNido
)

data class ResultNido(
    @SerializedName("id_Nido")
    val idNido: String,
    @SerializedName("Id_Coach")
    val idCoach: String
)
