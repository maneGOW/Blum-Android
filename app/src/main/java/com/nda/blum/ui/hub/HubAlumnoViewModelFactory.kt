package com.nda.blum.ui.hub

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HubAlumnoViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HubAlumnoViewModel::class.java)) {
            return HubAlumnoViewModel(
                application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}