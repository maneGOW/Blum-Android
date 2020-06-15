package com.nda.blum.ui.coachresult

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.FindUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class CoachResultViewModel(application: Application) : BaseViewModel(application) {

    val secureStorage = SecureStorage(getApplication())

    var coachName = MutableLiveData<String>()
    var coachProfilePicture = MutableLiveData<String>()

    init {
        _showProgressDialog.value = false
        getCoachName()
    }

    private fun getCoachName() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            val coachdata = susGetCoachData()
            println("COACH NAME ${coachdata!!.result.Nombre_Usuario}")
            coachName.value = coachdata.result.Nombre_Usuario
            coachProfilePicture.value = coachdata.result.Foto_Usuario
            _showProgressDialog.value = false
        }
    }

    private suspend fun susGetCoachData(): FindUser? {
        var findUserResponse: FindUser? = null
        val idCoach = secureStorage.getObject("idCoach", String::class.java)
        println("Id coach $idCoach")
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/datoscoach.php?Id_Coach=$idCoach")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), FindUser::class.java)
                    findUserResponse = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return findUserResponse
    }

}
