package com.nda.blum.ui.coachbio

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoachBioViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoachBioViewModel::class.java)) {
            return CoachBioViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}