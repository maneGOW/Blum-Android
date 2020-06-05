package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class CitasDisponiblesResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: List<ResultCita>
)

data class ResultCita(
    @SerializedName("Id_fecha")
    val idfecha: String,
    @SerializedName("fecha_disponible")
    val fechaDisponible: String,
    @SerializedName("hora_disponible")
    val horaDisponible: String,
    @SerializedName("disponible")
    val disponible: String
)