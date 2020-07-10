package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class NotificacionesResponse(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Result")
    val result: List<NotificacionesResult?>
)

data class NotificacionesResult(
    @SerializedName("Id_Cita")
    val Id_Cita: String?,
    @SerializedName("Id_Coach")
    val Id_Coach: String?,
    @SerializedName("Id_Alumno")
    val Id_Alumno: String?,
    @SerializedName("Estatus_Cita")
    val Estatus_Cita: String?,
    @SerializedName("Dia_Cita")
    val Dia_Cita: String?,
    @SerializedName("Hora_Cita")
    val Hora_Cita: String?,
    @SerializedName("Id_Fecha")
    val Id_Fecha: String?,
    @SerializedName("coach")
    val coach: String?

)