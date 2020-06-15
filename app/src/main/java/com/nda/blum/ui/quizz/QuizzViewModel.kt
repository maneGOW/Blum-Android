package com.nda.blum.ui.quizz

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.GetPreguntasResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class QuizzViewModel(application: Application) : BaseViewModel(application) {

    val percentage = MutableLiveData<Int>()

    init {
        setPreguntas()
    }

    private fun setPreguntas(){
        coroutineScope.launch {
            val preguntasResult = getPreguntasFromService()
            if(preguntasResult.code == "0"){
                println(preguntasResult.result.size)
                preguntasResult.result.forEach {
                    println("TEXTO: ${it.textoPregunta} RESPUESTAS: ${it.respuestas}")
                }
            }else{
                println("ocurrió un error al obtener las preguntas")
            }
        }
    }


    private suspend fun getPreguntasFromService(): GetPreguntasResponse {
        var parsedResponse : GetPreguntasResponse? = null
        withContext(Dispatchers.IO){
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/getPreguntas.php")
                .method("POST", body)
                .build()
            try{
                val response: Response = client.newCall(request).execute()
                if(response.code == 200){
                    parsedResponse = Gson().fromJson(response.body!!.string(), GetPreguntasResponse::class.java)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return parsedResponse!!
    }


}
