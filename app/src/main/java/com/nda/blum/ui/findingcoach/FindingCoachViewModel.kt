package com.nda.blum.ui.findingcoach

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.AsignarCoachResponse
import com.nda.blum.DAO.CreateaChatRoomResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class FindingCoachViewModel(application: Application) :
    BaseViewModel(application) {
    val nRespuesta = MutableLiveData<String>()
    val idUsuario = MutableLiveData<String>()
    val secureStorage = SecureStorage(getApplication())

    private val _onCoachFinded = MutableLiveData<Boolean>()
    val onCoachFinded : LiveData<Boolean>
    get() = _onCoachFinded

    init {
        _showProgressDialog.value = false
        _onCoachFinded.value = false
    }

    fun sendUserToAsignarCoachService(){
        coroutineScope.launch {
            _showProgressDialog.value = true
            val resultAsignarCoach = asignarCoachServive()
            if(resultAsignarCoach!!.code == "0"){
                println("el usuario se asignó al coach ${resultAsignarCoach.result.idCoach} y al nido ${resultAsignarCoach.result.idNido}")
                secureStorage.storeObject("idCoach", resultAsignarCoach.result.idCoach)
                secureStorage.storeObject("idNido", resultAsignarCoach.result.idNido)
                createChatRoom()
                _onCoachFinded.value = true
                _showProgressDialog.value = false
            }else{
                _onCoachFinded.value = false
                _showProgressDialog.value = false

            }
        }
    }

    private suspend fun createChatRoom(): CreateaChatRoomResponse? {
        var createChatRoomResult: CreateaChatRoomResponse? = null
        withContext(Dispatchers.IO) {
            val coachId = secureStorage.getObject("idCoach", String::class.java)
            val userId = secureStorage.getObject("idUsuario", String::class.java)
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/createChatChannel.php?nombrechatroom=$coachId-$userId-individual")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), CreateaChatRoomResponse::class.java)
                    createChatRoomResult = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
        return createChatRoomResult
    }

    private suspend fun asignarCoachServive(): AsignarCoachResponse? {
        var asignarCoachResponse: AsignarCoachResponse? = null
        withContext(Dispatchers.IO) {
            val nRespuesta = secureStorage.getObject("nRespuesta", String::class.java)
            val userId = secureStorage.getObject("idUsuario", String::class.java)
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/asignacoach.php?idusuario=$userId&respuesta=$nRespuesta")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), AsignarCoachResponse::class.java)
                    asignarCoachResponse = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return asignarCoachResponse
    }
}



