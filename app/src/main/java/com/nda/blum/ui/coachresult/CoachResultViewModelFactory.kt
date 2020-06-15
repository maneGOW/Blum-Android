package com.nda.blum.ui.coachresult

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoachResultViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoachResultViewModel::class.java)) {
            return CoachResultViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}