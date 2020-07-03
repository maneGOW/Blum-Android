package com.nda.blum.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.LoginResponse
import com.nda.blum.network.BlumAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class LoginViewModel(application: Application) :
    BaseViewModel(application) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val rememberMe = MutableLiveData<Boolean?>()

    val firstLaunch = MutableLiveData<Boolean>()
    val userRol = MutableLiveData<String>()

    var secureStorage: SecureStorage? = null

    private val _showErrorMessage = MutableLiveData<Boolean>()
    val showErrorMessage: LiveData<Boolean>
        get() = _showErrorMessage

    init {
        secureStorage = SecureStorage(getApplication())
        rememberMe.value = false
        _showErrorMessage.value = false
        _showProgressDialog.value = false
        if (rememberMe.value != null) {
            if (rememberMe.value!!) {
                getRememberme()
            }
        }

    }

    fun onMessageShowed() {
        _showErrorMessage.value = false
    }

    fun userLogin(userMail: String, userPassword: String){
        _showProgressDialog.value = true
        BlumAPI().getCustomService().login(userMail,userPassword).enqueue(object:
            Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("BLUM", "Error en servicio https: " + t.stackTrace)
                _showProgressDialog.value = false
            }

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                Log.e("BLUM", "login response "+ response.toString())
                _showProgressDialog.value = false
                if(response.code() == HTTP_OK){
                    if(response.body()!!.code == "0"){
                        println("login correcto")
                        println("RESPONSE LOGIN " + response.body()!!.result.rollUsuario)

                        secureStorage!!.storeObject("idUsuario", response.body()!!.result.idUsuario)
                        secureStorage!!.storeObject("nombreUsuario", response.body()!!.result.nombreUsuario)
                        secureStorage!!.storeObject("correoUsuario", response.body()!!.result.correoUsuario)
                        secureStorage!!.storeObject("rolUsuario", response.body()!!.result.rollUsuario)
                        secureStorage!!.storeObject("userProfilePicture", response.body()!!.result.Foto_Usuario)
                        secureStorage!!.storeObject("passwordUsuario", password.value!!)
                        secureStorage!!.storeObject("idCoach", response.body()!!.result.idCoach)
                        secureStorage!!.storeObject("idNido", response.body()!!.result.idNido)

                        checkFristLaunch()
                    }
                }
            }
        })
    }

    /*
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
     */

    private fun checkFristLaunch() {
        val getFirstLaunchValue = secureStorage!!.getObject("firstLaunch", Boolean::class.java)
        val getUserRol = secureStorage!!.getObject("rolUsuario", String::class.java)
        println("LOGIN VIEW MODEL: $getFirstLaunchValue ---- $getUserRol")
        firstLaunch.value = getFirstLaunchValue
        userRol.value = getUserRol
    }

    fun storeRememberme(value: Boolean){
        secureStorage!!.storeObject("rememberMe", value)
    }

    fun getRememberme() {
        val rememberMe = secureStorage!!.getObject("rememberMe", Boolean::class.java)
        if (rememberMe!!) {
            val userEmail = secureStorage!!.getObject("correoUsuario", String::class.java)
            val userPassword = secureStorage!!.getObject("passwordUsuario", String::class.java)
            email.value = userEmail
            password.value = userPassword
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
