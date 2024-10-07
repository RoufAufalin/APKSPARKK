package com.ui.pilihparkir

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.Result
import com.data.response.BlokResponse
import com.data.response.SeatStatusResponse
import kotlinx.coroutines.launch

class PilihParkirViewModel(val repository : Repository) : ViewModel() {

    private val _result = MutableLiveData<Result<BlokResponse>>()
    var result : LiveData<Result<BlokResponse>> = _result

    private val _hasil = MutableLiveData<Result<SeatStatusResponse>>()
    var hasil : LiveData<Result<SeatStatusResponse>> = _hasil

    fun getSlotBlok() {
        viewModelScope.launch {
            _result.value = Result.Loading

            try {
                val response = repository.getSlotBlok(2)
                _result.value = response
            } catch (e : Exception) {
                _result.value = Result.Error(e, e.message ?: "Unknown error")
            }
        }
    }

    fun getSeatStatus() {
        viewModelScope.launch {
            _hasil.value = Result.Loading

            try {
                val response = repository.getSeatStatus(1)
                _hasil.value = response
            } catch (e : Exception) {
                _result.value = Result.Error(e, e.message ?: "Unknown error")
            }
        }
    }

}