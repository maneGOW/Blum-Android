package com.nda.blum.ui.nidosdata

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class NidosDataViewModel(application: Application) : BaseViewModel(application) {

    val nidoIDLiveData = MutableLiveData<String>()
    val alumnosNido = MutableLiveData<UsuariosNidoResponse>()

    init{
        nidoIDLiveData.value = ""
    }

    fun getNidosList() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            val usuariosNidosData = susGetNidosList()
            if(usuariosNidosData!!.code == "0"){
                alumnosNido.value = usuariosNidosData
            }
            else{
                _showProgressDialog.value = false
            }
        }
    }

    fun updateNidoData(nombre: String) {
        coroutineScope.launch {
            susUpdateNidoData(nombre)
        }
    }

    suspend fun susUpdateNidoData(nombre: String){
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/ActualizarNombreNido.php?id_nido=${nidoIDLiveData.value}&nombre_nido=$nombre")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), ActualizarDatosNidoResponse::class.java)
                    println(Gson().toJson(parsedResponse))
                    // uploadPictureResponse = parsedResponse
                }else{
                    println(response.code)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun susGetNidosList(): UsuariosNidoResponse? {
        var findUserResponse: UsuariosNidoResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/BuscarMiembrosNido.php?nidoId=${nidoIDLiveData.value}")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            println("URL ${request.url}")
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), UsuariosNidoResponse::class.java)
                    findUserResponse = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return findUserResponse
    }
}