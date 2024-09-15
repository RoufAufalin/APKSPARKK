package com.ui.pilihparkir

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.Result
import com.data.response.BlokResponse
import kotlinx.coroutines.launch

class PilihParkirViewModel(val repository : Repository) : ViewModel() {

    private val _result = MutableLiveData<Result<BlokResponse>>()
    var result : LiveData<Result<BlokResponse>> = _result

    fun getSlotBlok() {
        viewModelScope.launch {
            _result.value = Result.Loading

            try {
                val response = repository.getSlotBlok(1)
                _result.value = response
            } catch (e : Exception) {
                _result.value = Result.Error(e, e.message ?: "Unknown error")
            }
        }
    }

}