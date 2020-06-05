package com.nda.blum.DAO

import com.google.gson.annotations.SerializedName

data class GetPreguntasResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<ResultPreguntas>
)

data class ResultPreguntas(
    @SerializedName("Id_pregunta")
    val idpregunta: String,
    @SerializedName("texto_pregunta")
    val textoPregunta: String,
    @SerializedName("Id_respuesta")
    val idRespuesta: String,
    @SerializedName("respuesta")
    val respuesta: String,
    @SerializedName("respuestas")
    val respuestas: String
)
