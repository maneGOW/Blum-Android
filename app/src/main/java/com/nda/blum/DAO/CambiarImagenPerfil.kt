package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class CambiarImagenPerfil (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: String?
)

