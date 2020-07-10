package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class RecursosResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: List<RecursosResult>
)

data class RecursosResult(
    @SerializedName("archivo")
    val nombreArchivo: String
)