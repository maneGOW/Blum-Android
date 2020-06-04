package com.nda.blum

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application) : AndroidViewModel(application){

    val _showProgressDialog = MutableLiveData<Boolean>()
    val showProgressDialog: LiveData<Boolean>
        get() = _showProgressDialog

    init {
        _showProgressDialog.value = false
    }

}