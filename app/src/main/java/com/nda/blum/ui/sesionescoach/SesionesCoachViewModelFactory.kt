package com.nda.blum.ui.sesionescoach

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nda.blum.db.dao.UserDao

class SesionesCoachViewModelFactory(
    private val application: Application,
    private val dataSource: UserDao
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SesionersCoachViewModel::class.java)) {
            return SesionersCoachViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}