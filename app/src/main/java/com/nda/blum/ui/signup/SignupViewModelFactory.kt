package com.nda.blum.ui.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignupViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}