package com.shawonshagor0.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    private var t1score = MutableLiveData<Int>(0)
    private var t2score = MutableLiveData<Int>(0)
    private var t1set = MutableLiveData<Int>(0)
    private var t2set = MutableLiveData<Int>(0)


    val _t1score : LiveData<Int>
        get() = t1score

    val _t2score : LiveData<Int>
        get() = t2score

    val _t1set : LiveData<Int>
        get() = t1set

    val _t2set : LiveData<Int>
        get() = t2set

    fun addSetToTeam1 (){
        t1set.value = t1set.value!! + 1
    }
    fun addSetToTeam2 (){
        t2set.value = t2set.value!! + 1
    }

    fun addScoreToTeam1 (){
        t1score.value = t1score.value!! + 1

    }
    fun addScoreToTeam2 (){
        t2score.value = t2score.value!! + 1
    }

    fun bothZero(){
        t1score.postValue(0)
        t2score.postValue(0)
    }
    fun setBothZero(){
        t1set.postValue(0)
        t2set.postValue(0)
    }

}