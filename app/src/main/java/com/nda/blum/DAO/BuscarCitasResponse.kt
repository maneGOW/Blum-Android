package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class BuscarCitasResponse (
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: List<CitasEncontradas>
)

data class CitasEncontradas(
    @SerializedName("Hora_Cita")
    val horaCita: String,
    @SerializedName("Nombre_Alumno")
    val nombreAlumno: String
)
