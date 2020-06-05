package com.nda.blum.ui.hub

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nda.blum.db.dao.UserDao
import com.nda.blum.db.entity.FirstLaunch
import com.nda.blum.db.entity.User
import kotlinx.coroutines.*

class HubAlumnoViewModel(private val database: UserDao, application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val userName = MutableLiveData<String?>()
    val userRol = MutableLiveData<String?>()

    init {
        userName.value = ""
        updateFirstLaunch()
    }

    fun setUserName(){
        coroutineScope.launch {
            try{
                val userdata = getUserData()
                println(userdata!!.userNombreUsuario)
                userName.value = userdata.userNombreUsuario
                userRol.value = userdata.userRol
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun updateFirstLaunch(){
        coroutineScope.launch {
            susInsertFirstLaunch()
        }
    }

    private suspend fun susInsertFirstLaunch(){
        withContext(Dispatchers.IO) {
            try{
                val getFirstLaunch = database.getFristLaunchValue()
                val insertFirstLaunch = FirstLaunch(1,false)
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

    private suspend fun getUserData(): User? {
        return withContext(Dispatchers.IO) {
            val user = database.getAllUserData()
            user
        }
    }

}
