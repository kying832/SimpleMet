package com.example.simplemet.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
// TODO: Implement the ViewModel
class MainViewModel : ViewModel() {
    private val _tempo = MutableLiveData<Int>()
    val tempo: LiveData<Int> = _tempo


    init{
        resetMet()
    }
/*
* Computes time delay between beats or frequency based on beats per minute
* returns time in seconds.
* */
    fun getFrequency(): Double {
        return 1.0/ tempo.value!!.times(1.0 / 60.0)
    }

    private fun resetMet(){
        _tempo.value = 40
    }

    fun setTempo(v: Int){
        _tempo.value = v
    }
}
