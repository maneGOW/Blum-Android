package com.nda.blum.ui.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nda.blum.db.dao.UserDao
import com.nda.blum.db.entity.FirstLaunch

class SignupViewModelFactory(
    private val application: Application,
    private val firstLaunchDao: UserDao
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(
                firstLaunchDao,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}