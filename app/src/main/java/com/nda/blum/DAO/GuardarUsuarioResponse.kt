package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class GuardarUsuarioResponse (
    @SerializedName("message")
    val message: String
)