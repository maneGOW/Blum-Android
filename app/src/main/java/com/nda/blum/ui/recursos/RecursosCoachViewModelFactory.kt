package com.nda.blum.ui.recursos

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nda.blum.ui.recoverpassword.RecuperarPasswordViewModel

class RecursosCoachViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecursosCoachViewModel::class.java)) {
            return RecursosCoachViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}