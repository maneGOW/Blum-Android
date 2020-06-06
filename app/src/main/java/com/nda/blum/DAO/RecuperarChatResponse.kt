package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class RecuperarChatResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: List<RestultChatMessage>
)

data class RestultChatMessage(
    @SerializedName("Id_Chat")
    val Id_Chat: String,
    @SerializedName("IdUsuarioEmisor_Chat")
    val IdUsuarioEmisor_Chat: String,
    @SerializedName("IdUsuarioReceptor_Chat")
    val IdUsuarioReceptor_Chat: String,
    @SerializedName("Mensaje_Chat")
    val Mensaje_Chat: String
)
