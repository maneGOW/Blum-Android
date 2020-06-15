package com.nda.blum.DAO
import com.google.gson.annotations.SerializedName

data class FindCoachResponse(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Result")
    val result: FindCoachResult
)

data class FindCoachResult (
    @SerializedName("Id_Coach")
    val Id_Coach: String,
    @SerializedName("Id_Usuario")
    val Id_Usuario: String,
    @SerializedName("Nombre_Coach")
    val Nombre_Coach: String,
    @SerializedName("Edad_Coach")
    val Edad_Coach: String,
    @SerializedName("Nacionalidad_Coach")
    var Nacionalidad_Coach: String,
    @SerializedName("SeptenioHijos_Coach")
    var SeptenioHijos_Coach: String,
    @SerializedName("Correo_Coach")
    var Correo_Coach: String,
    @SerializedName("Numero_Coach")
    var Numero_Coach: String,
    @SerializedName("Foto_Coach")
    var Foto_Coach: String,
    @SerializedName("Especialidad_Coach")
    var Especialidad_Coach: String,
    @SerializedName("Resena_Coach")
    var Resena_Coach: String
)
