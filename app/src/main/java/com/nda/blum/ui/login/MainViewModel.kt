package com.nda.blum.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.DAO.LoginResponse
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class MainViewModel(application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _navigateToHub = MutableLiveData<Boolean>()
    val navigateToHub : LiveData<Boolean>
    get() = _navigateToHub

    init {
        _navigateToHub.value = false
    }

    fun callLoginService() {
        coroutineScope.launch {
            if(login()){
                _navigateToHub.value = true
            }
        }
    }

    fun onHubNavigated(){
        _navigateToHub.value = false
    }

    private suspend fun login(): Boolean{
        var success = false
        withContext(Dispatchers.IO){
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/login.php?email=${email.value}&password=${password.value}")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            if(response.code == 200){
                val parsedResponse = Gson().fromJson(response.body!!.string(), LoginResponse::class.java)
                println("RESPONSE: $parsedResponse")
                success = parsedResponse.code == "0"
            }
        }
        return success
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
