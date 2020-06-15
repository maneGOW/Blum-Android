package com.nda.blum.ui.quizz

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuizzViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizzViewModel::class.java)) {
            return QuizzViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}