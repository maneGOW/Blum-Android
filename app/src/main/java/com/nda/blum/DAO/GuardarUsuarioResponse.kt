package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class GuardarUsuarioResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String
)