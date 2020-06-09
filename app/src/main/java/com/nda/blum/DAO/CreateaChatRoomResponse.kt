package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class CreateaChatRoomResponse (
    @SerializedName("Code")
    val Code: String,
    @SerializedName("Message")
    val Message: String,
    @SerializedName("Result")
    val Result: String
)