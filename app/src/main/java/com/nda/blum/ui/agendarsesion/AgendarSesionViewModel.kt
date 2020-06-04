package com.nda.blum.ui.agendarsesion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.CitasDisponiblesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class AgendarSesionViewModel(application: Application) : BaseViewModel(application){

    //https://retosalvatucasa.com/ws_app_nda/buscardisponibles.php?idcoach=1&fecha=20/09/20202

    val idCoach = MutableLiveData<String>()
    val fecha = MutableLiveData<String>()

    private suspend fun getCitasDisponibles(): CitasDisponiblesResponse {
        var responseCitas : CitasDisponiblesResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/buscardisponibles.php?idcoach=${idCoach.value}&fecha=${fecha.value}")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), CitasDisponiblesResponse::class.java)
                    println("RESPONSE: $parsedResponse")
                    responseCitas = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return responseCitas!!
    }
}
