package com.nda.blum

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel(application: Application) : AndroidViewModel(application){

    val _showProgressDialog = MutableLiveData<Boolean>()
    val showProgressDialog: LiveData<Boolean>
        get() = _showProgressDialog

    var viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _showProgressDialog.value = false
    }

}