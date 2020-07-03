package com.nda.blum.ui.nidosdata

import android.app.Application
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.BuscarNidoCoachResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class NidosDataViewModel(application: Application) : BaseViewModel(application) {
    /*
    val secureStorage = SecureStorage(getApplication())
    private fun getNidosList() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            val nidosData = susGetNidosList()
            if(nidosData!!.code == "0"){
                nidosList.value = nidosData
                _obtenidoNidosList.value = true
                _showProgressDialog.value = false
            }
            else{
                _showProgressDialog.value = false
            }
        }
    }*/
/*
    private suspend fun susGetNidosList(): BuscarNidoCoachResponse? {
        var findUserResponse: BuscarNidoCoachResponse? = null
        val isUsuario = secureStorage.getObject("idUsuario", String::class.java)
        println("Id coach $isUsuario")
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/buscanidoscoach.php?idcoach=$isUsuario")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), BuscarNidoCoachResponse::class.java)
                    findUserResponse = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return findUserResponse
    }*/
}