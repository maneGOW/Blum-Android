package com.nda.blum.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.LoginResponse
import com.nda.blum.db.dao.UserDao
import com.nda.blum.db.entity.FirstLaunch
import com.nda.blum.db.entity.User
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class LoginViewModel(private val database: UserDao, application: Application) :
    BaseViewModel(application) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val rememberMe = MutableLiveData<Boolean?>()

    val firstLaunch = MutableLiveData<Boolean>()
    val userRol = MutableLiveData<String>()

    private val _showErrorMessage = MutableLiveData<Boolean>()
    val showErrorMessage: LiveData<Boolean>
        get() = _showErrorMessage

    init {
        _showErrorMessage.value = false
        _showProgressDialog.value = false
        if(rememberMe.value != null){
            if(rememberMe.value!!){
                getRememberme()
            }
        }

    }

    fun callLoginService() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            if (login()) {
                _showProgressDialog.value = false
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
                        val userData = User(
                            1,
                            parsedResponse.result.idUsuario,
                            "",
                            "",
                            parsedResponse.result.nombreUsuario,
                            parsedResponse.result.correoUsuario,
                            parsedResponse.result.rollUsuario,
                            parsedResponse.result.telefonoUsuario,
                            "",
                            false,
                            password.value
                        )
                        saveUser(userData)
                        checkFristLaunch()
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

    private fun saveUser(user: User) {
        try {
            coroutineScope.launch {
                updateUserdata(user)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateRememberme(bandera: Boolean){
        coroutineScope.launch {
            updateRemembermeVal(bandera)
        }
    }

    private suspend fun updateRemembermeVal(bandera: Boolean){
        withContext(Dispatchers.IO) {
            database.updateRemembermeValue(bandera)
            println("DATOS DE REMEMBERME ACTUALIZADOS")
        }
    }

    fun getRememberme(){
        coroutineScope.launch {
            val getuserData = susGetUserData()
            if(getuserData != null){
                if(getuserData.rememberme!!){
                    email.value = getuserData.userCorreoElectronico
                    password.value = getuserData.password
                }
            }else{
                println("no hay usuario")
            }
        }
    }


    private fun checkFristLaunch() {
        coroutineScope.launch {
            val getFirstLaunchValue = susFirstLaunch()
            if (getFirstLaunchValue != null) {
                val getUserData = susGetUserData()
                if (getUserData != null) {
                    firstLaunch.value = getFirstLaunchValue.firstLaunch
                    userRol.value = getUserData.userRol
                } else {
                    println("DATOS NULOS DEL USUARIO")
                }
            } else {
                println("DATOS NULOS DEL FIRST LAUNCH")
                val getUserData = susGetUserData()
                if (getUserData != null) {
                    firstLaunch.value = false
                    userRol.value = getUserData.userRol
                } else {
                    println("DATOS NULOS DEL USUARIO")
                }

            }
        }
    }

    private suspend fun susGetUserData(): User? {
        return withContext(Dispatchers.IO) {
            database.getAllUserData()
        }
    }

    private suspend fun susFirstLaunch(): FirstLaunch? {
        return withContext(Dispatchers.IO) {
            database.getFristLaunchValue()
        }
    }

    private suspend fun updateUserdata(user: User) {
        withContext(Dispatchers.IO) {
            try {
                val userRegistered = database.getAllUserData()
                if (userRegistered == null) {
                    val newUser = User(1, "", "", "", "", "", "", "", "",false, "")
                    database.insertUser(newUser)
                    database.loginUpdate(user.userServerId!!, user.userNombreUsuario!!, user.userCorreoElectronico!!, user.userRol!!, user.userTelefonoUsuario!!)
                    println("Datos de usuario de login agregados y actualizado")
                } else {
                    database.loginUpdate(user.userServerId!!, user.userNombreUsuario!!, user.userCorreoElectronico!!, user.userRol!!, user.userTelefonoUsuario!!)
                    println("Datos de usuario de login actualzados")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
