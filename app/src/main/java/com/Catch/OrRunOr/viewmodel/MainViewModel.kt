package com.Catch.OrRunOr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Catch.OrRunOr.model.Position

class MainViewModel : ViewModel() {

    private val _location = MutableLiveData<Position>()
    val location: LiveData<Position>
        get() = _location

    fun lastLocation(position: Position) {
        _location.value = position
    }
}