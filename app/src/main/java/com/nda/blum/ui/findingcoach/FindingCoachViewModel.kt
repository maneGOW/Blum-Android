package com.nda.blum.ui.findingcoach

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.AsignarCoachResponse
import com.nda.blum.DAO.CreateaChatRoomResponse
import com.nda.blum.db.dao.UserDao
import com.nda.blum.db.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class FindingCoachViewModel(private val dataSource: UserDao, application: Application) :
    BaseViewModel(application) {
    val nRespuesta = MutableLiveData<String>()
    val idUsuario = MutableLiveData<String>()

    private val _onCoachFinded = MutableLiveData<Boolean>()
    val onCoachFinded : LiveData<Boolean>
    get() = _onCoachFinded

    init {
        _onCoachFinded.value = false
    }

    fun sendUserToAsignarCoachService(){
        coroutineScope.launch {
            val resultAsignarCoach = asignarCoachServive()
            if(resultAsignarCoach!!.code == "0"){
                println("el usuario se asignó al coach ${resultAsignarCoach.result.idCoach} y al nido ${resultAsignarCoach.result.idNido}")
                susUpdateCoachAndNido(resultAsignarCoach.result.idCoach,resultAsignarCoach.result.idNido)
                createChatRoom()
                _onCoachFinded.value = true
            }else{
                _onCoachFinded.value = false
            }
        }
    }

    private suspend fun createChatRoom(): CreateaChatRoomResponse? {
        var createChatRoomResult: CreateaChatRoomResponse? = null
        withContext(Dispatchers.IO) {
            val userData = susGetUserData()
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/createChatChannel.php?nombrechatroom=${userData!!.coachId}-${userData.userServerId}-individual")
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
            val userData = susGetUserData()
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/asignacoach.php?idusuario=${userData!!.userServerId}&respuesta=${nRespuesta.value}")
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

    private suspend fun susGetUserData(): User? {
        return withContext(Dispatchers.IO) {
            dataSource.getAllUserData()
        }
    }

    private suspend fun susUpdateCoachAndNido(coachId: String, idNido:String){
        withContext(Dispatchers.IO){
            dataSource.updatecoachIdAndNest(coachId, idNido)
        }
    }

}



