package com.nda.blum.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.DAO.GuardarUsuarioResponse
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val nombre = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val celular = MutableLiveData<String>()

    fun saveUserData() {
        coroutineScope.launch {
            registrarUsuario()
        }
    }

    suspend fun registrarUsuario(){
        withContext(Dispatchers.IO){
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/guardarusuario.php?nombre=${nombre.value}&email=${email.value}&password=${password.value}&telefono=${celular.value}")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            if(response.code == 200){
                val parsedResponse = Gson().fromJson(response.body!!.string(), GuardarUsuarioResponse::class.java)
                println("RESPONSE: $parsedResponse")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}