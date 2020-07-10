package com.nda.blum.ui.recursos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel

class RecursosCoachViewModel(application: Application) : BaseViewModel(application) {

    val userProfilePicture = MutableLiveData<String?>()
    val secureStorage = SecureStorage(getApplication())

    init {
        userProfilePicture.value = ""
        val profilePicture = secureStorage.getObject("userProfilePicture", String::class.java)
        userProfilePicture.value = profilePicture
    }
}
