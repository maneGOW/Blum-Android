package com.nda.blum.ui.findingcoach

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FindingCoachViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FindingCoachViewModel::class.java)) {
            return FindingCoachViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}