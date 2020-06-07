package com.nda.blum.ui.sesionescoach

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.BuscarCitasResponse
import com.nda.blum.db.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class SesionersCoachViewModel(private val database: UserDao, application: Application) :
    BaseViewModel(application) {

    val citasDelServer = MutableLiveData<BuscarCitasResponse>()

    private val _filledCitasDelServer = MutableLiveData<Boolean>()
    val filledCitasDelServer: LiveData<Boolean>
        get() = _filledCitasDelServer

    init {
        getCitasFromServer()
    }

    fun getCitasFromServer() {
        coroutineScope.launch {
            val getMessages = susGetCitas()
            if (getMessages!!.code == "0") {
                citasDelServer.value = getMessages
                getMessages.result.forEach {
                    println("citas: ${it.nombreAlumno}-${it.horaCita}")
                    _filledCitasDelServer.value = true
                }
            } else {
                println("SIN CHATS")
                _filledCitasDelServer.value = false
            }
        }
    }

    private suspend fun susGetCitas(): BuscarCitasResponse? {
        var buscarCitasResult: BuscarCitasResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/buscarcitas.php?idcoach=1&diacita=05/06/2020")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), BuscarCitasResponse::class.java)
                    buscarCitasResult = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return buscarCitasResult
    }

}
