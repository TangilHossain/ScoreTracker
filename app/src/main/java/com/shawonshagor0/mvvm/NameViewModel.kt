package com.shawonshagor0.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NameViewModel : ViewModel() {
    private var _name1 = MutableLiveData<String>("Player1")
    private var _name2 = MutableLiveData<String>("Player2")

    val name1: LiveData<String>
        get() = _name1
    val name2: LiveData<String>
        get() = _name2

    fun setNames(name1: String, name2: String){
        _name1.value = name1
        _name2.value = name2
    }
}