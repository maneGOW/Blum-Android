package com.nda.blum.ui.signup

import android.app.Application
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.GuardarUsuarioResponse
import com.nda.blum.db.dao.UserDao
import com.nda.blum.db.entity.FirstLaunch
import com.nda.blum.db.entity.User
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response


class SignupViewModel(private val database: UserDao, application: Application) : BaseViewModel(application) {

    val nombre = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val celular = MutableLiveData<String>()

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin : LiveData<Boolean>
            get() = _navigateToLogin

    init {
        _navigateToLogin.value = false
    }

    fun saveUserData() {
        coroutineScope.launch {
            try{
                _showProgressDialog.value = true
                val result = registrarUsuario()
                if(result.code == "0"){
                    println("RESPONSE: $result")
                    println("Usuario registrado en blum")
                    _showProgressDialog.value = false
                    _navigateToLogin.value = true
                }else{
                    println("error al almacenar el usuario")
                    _showProgressDialog.value = false

                }
            }catch (e:Exception){
                e.printStackTrace()
                _showProgressDialog.value = false
            }
        }
    }

    fun onNavigated(){
        _navigateToLogin.value = false
    }

    suspend fun registrarUsuario(): GuardarUsuarioResponse{
        var parsedResponse : GuardarUsuarioResponse? = null
        withContext(Dispatchers.IO){
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/guardarusuario.php?nombre=${nombre.value}&email=${email.value}&password=${password.value}&telefono=${celular.value}")
                .method("POST", body)
                .build()
            try{
                val response: Response = client.newCall(request).execute()
                if(response.code == 200){
                    parsedResponse = Gson().fromJson(response.body!!.string(), GuardarUsuarioResponse::class.java)
                    insertFirtsLaunch()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return parsedResponse!!
    }

    private fun insertFirtsLaunch(){
        coroutineScope.launch {
            susInsertFirstLaunch()
        }
    }

    private suspend fun susInsertFirstLaunch(){
         withContext(Dispatchers.IO) {
             try{
                 val getFirstLaunch = database.getFristLaunchValue()
                 val insertFirstLaunch = FirstLaunch(1,true)
                 if (getFirstLaunch == null) {
                     database.insertFirstLaunch(insertFirstLaunch)
                     println("first launch insertado")
                 } else {
                     database.updateFirstLaunch(insertFirstLaunch)
                     println("first launch actualizado")
                 }
             }catch (e:Exception){
                 e.printStackTrace()
             }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
