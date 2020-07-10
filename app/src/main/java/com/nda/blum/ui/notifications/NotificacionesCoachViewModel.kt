package com.nda.blum.ui.notifications

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.NotificacionesResponse
import com.nda.blum.DAO.UsuariosNidoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class NotificacionesCoachViewModel(application: Application) : BaseViewModel(application) {

    val secureStorage = SecureStorage(getApplication())

    var notificationsLiveData = MutableLiveData<NotificacionesResponse>()

    fun getNotificaciones() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            val idUsuario = secureStorage.getObject("idUsuario", String::class.java)
            var idCoach = secureStorage.getObject("idCoach", String::class.java)
            if(idCoach.isNullOrEmpty()){
                idCoach = "0"
            }
            val usuariosNidosData = susGetNotificaciones(idUsuario!!,idCoach)
            if(usuariosNidosData!!.code == "0"){
                notificationsLiveData.value = usuariosNidosData
            }
            else if(usuariosNidosData.code == "1"){
                notificationsLiveData.value = usuariosNidosData
            }
            else{
                _showProgressDialog.value = false
            }
        }
    }

    private suspend fun susGetNotificaciones(idUsuario: String, idCoach: String): NotificacionesResponse? {
        var findUserResponse: NotificacionesResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/buscacitasAlumno.php?idusuario=$idUsuario&idCoach=$idCoach")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            println("URL ${request.url}")
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), NotificacionesResponse::class.java)
                    findUserResponse = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return findUserResponse
    }

}
