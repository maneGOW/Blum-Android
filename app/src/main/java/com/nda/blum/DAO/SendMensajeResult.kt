package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class SendMensajeResult (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String
)
