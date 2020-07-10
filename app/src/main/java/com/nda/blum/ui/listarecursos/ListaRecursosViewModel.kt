package com.nda.blum.ui.listarecursos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.RecursosResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class ListaRecursosViewModel(application: Application) : BaseViewModel(application) {

    val userProfilePicture = MutableLiveData<String?>()
    val recursosListLiveData = MutableLiveData<RecursosResponse>()
    val secureStorage = SecureStorage(getApplication())


    init {
        userProfilePicture.value = ""
        val profilePicture = secureStorage.getObject("userProfilePicture", String::class.java)
        userProfilePicture.value = profilePicture
    }

    fun getFilesFromServer() {
        coroutineScope.launch {
            val recursos = susGetFileListFromServer()
            recursosListLiveData.value = recursos
        }
    }

    suspend fun susGetFileListFromServer(): RecursosResponse{
        var recursosResult: RecursosResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://www.retosalvatucasa.com/ws_app_nda/BuscaArchivos.php")
                .method("POST", body)
                .build()

            println("URL DEL SERVICIO DE RECURSOS ${request.url}")

            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), RecursosResponse::class.java)
                    recursosResult = parsedResponse
                }else{
                    println(response.code)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return recursosResult!!
    }
}