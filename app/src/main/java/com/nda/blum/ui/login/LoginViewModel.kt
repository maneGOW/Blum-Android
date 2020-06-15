package com.nda.blum.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.LoginResponse
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class LoginViewModel(application: Application) :
    BaseViewModel(application) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val rememberMe = MutableLiveData<Boolean?>()

    val firstLaunch = MutableLiveData<Boolean>()
    val userRol = MutableLiveData<String>()

    val secureStorage = SecureStorage(getApplication())

    private val _showErrorMessage = MutableLiveData<Boolean>()
    val showErrorMessage: LiveData<Boolean>
        get() = _showErrorMessage

    init {
        _showErrorMessage.value = false
        _showProgressDialog.value = false
        if (rememberMe.value != null) {
            if (rememberMe.value!!) {
                getRememberme()
            }
        }

    }

    fun callLoginService() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            userRol.value = secureStorage.getObject("rolUSuario", String::class.java)
            if (login()) {
                _showProgressDialog.value = false
                checkFristLaunch()
            } else {
                _showErrorMessage.value = true
                _showProgressDialog.value = false

            }
        }
    }

    fun onMessageShowed() {
        _showErrorMessage.value = false
    }

    private suspend fun login(): Boolean {
        var success = false
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/login.php?email=${email.value}&password=${password.value}")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), LoginResponse::class.java)
                    println("RESPONSE: $parsedResponse")
                    if (parsedResponse.code == "0") {
                        success = true
                        secureStorage.storeObject("idUsuario", parsedResponse.result.idUsuario)
                        secureStorage.storeObject(
                            "nombreUsuario",
                            parsedResponse.result.nombreUsuario
                        )
                        secureStorage.storeObject(
                            "correoUsuario",
                            parsedResponse.result.correoUsuario
                        )
                        secureStorage.storeObject("rolUsuario", parsedResponse.result.rollUsuario)
                        secureStorage.storeObject(
                            "telefonoUsuario",
                            parsedResponse.result.telefonoUsuario
                        )
                        secureStorage.storeObject("userProfilePicture", parsedResponse.result.Foto_Usuario)
                        secureStorage.storeObject("passwordUsuario", password.value!!)
                        secureStorage.storeObject("idCoach", parsedResponse.result.idCoach)
                        secureStorage.storeObject("idNido", parsedResponse.result.idNido)

                    } else {
                        success = false
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return success
    }

    private fun checkFristLaunch() {
        val getFirstLaunchValue = secureStorage.getObject("firstLaunch", Boolean::class.java)
        val getUserRol = secureStorage.getObject("rolUsuario", String::class.java)
        println("LOGIN VIEW MODEL: $getFirstLaunchValue ---- $getUserRol")
        firstLaunch.value = getFirstLaunchValue
        userRol.value = getUserRol
    }

    fun getRememberme() {
        val rememberMe = secureStorage.getObject("rememberMe", Boolean::class.java)
        if (rememberMe!!) {
            val userEmail = secureStorage.getObject("correoUsuario", String::class.java)
            val userPassword = secureStorage.getObject("passwordUsuario", String::class.java)
            email.value = userEmail
            password.value = userPassword
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
