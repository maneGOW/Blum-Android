package com.nda.blum.ui.hub

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jcloquell.androidsecurestorage.SecureStorage
import kotlinx.coroutines.*

class HubAlumnoViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val secureStorage = SecureStorage(getApplication())

    val userName = MutableLiveData<String?>()
    val userRol = MutableLiveData<String?>()
    val userProfilePicture = MutableLiveData<String?>()

    init {
        userName.value = ""
        userProfilePicture.value = ""
        updateFirstLaunch()
    }

    fun setUserName() {
        try {
            val nombreUsuario = secureStorage.getObject("nombreUsuario", String::class.java)
            val rolUsuario = secureStorage.getObject("rolUsuario", String::class.java)
            val profilePicture = secureStorage.getObject("userProfilePicture", String::class.java)
            println(nombreUsuario)
            println("URL PROFILE PICTURE $profilePicture")
            userName.value = nombreUsuario
            userRol.value = rolUsuario
            userProfilePicture.value = profilePicture
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateFirstLaunch() {
        try {
            secureStorage.storeObject("firstLaunch", false)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}
