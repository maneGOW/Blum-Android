package com.nda.blum.ui.bluumerscoach

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.BuscarAlumnosCoachResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class BlummersCoachViewModel(application: Application) : BaseViewModel(application) {

    var alumnosList = MutableLiveData<BuscarAlumnosCoachResponse>()
    val secureStorage = SecureStorage(getApplication())

    private val _obtenidoAlumnosList = MutableLiveData<Boolean>()
    val obtenidoAlumnosList: LiveData<Boolean>
        get() = _obtenidoAlumnosList

    private val _navigateToAlumnoChat = MutableLiveData<String>()
    val navigateToAlumnoChat: LiveData<String>
        get() = _navigateToAlumnoChat

    fun onAlumnoClicked(id: String){
        _navigateToAlumnoChat.value = id
    }

    fun onAlumnoNavigated(){
        _navigateToAlumnoChat.value = null
    }

    init {
        _showProgressDialog.value = false
        getNidosList()
    }

    private fun getNidosList() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            val alumnosData = susGetAlumnosList()
            if(alumnosData!!.code == "0"){
                alumnosList.value = alumnosData
                _obtenidoAlumnosList.value = true
                _showProgressDialog.value = false
            }
            else{
                _showProgressDialog.value = false
            }
        }
    }

    private suspend fun susGetAlumnosList(): BuscarAlumnosCoachResponse? {
        var findUserResponse: BuscarAlumnosCoachResponse? = null
        val isUsuario = secureStorage.getObject("idUsuario", String::class.java)
        println("Id coach $isUsuario")
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/buscaalumnosporcoach.php?idcoach=$isUsuario")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), BuscarAlumnosCoachResponse::class.java)
                    findUserResponse = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return findUserResponse
    }

}
