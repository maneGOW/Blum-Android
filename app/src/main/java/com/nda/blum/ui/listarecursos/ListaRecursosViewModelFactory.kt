package com.nda.blum.ui.listarecursos

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nda.blum.ui.recursos.RecursosCoachViewModel

class ListaRecursosViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaRecursosViewModel::class.java)) {
            return ListaRecursosViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}