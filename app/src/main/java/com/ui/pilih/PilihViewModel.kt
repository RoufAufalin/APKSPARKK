package com.ui.pilih

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.Result
import com.data.response.SlotResponse
import kotlinx.coroutines.launch

class PilihViewModel(private val repository: Repository): ViewModel() {
    private val _result = MutableLiveData<Result<SlotResponse>>()
    val result : LiveData<Result<SlotResponse>> = _result

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> =  _isLoading


    fun getSlotParkir() {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = repository.getSlot()
                _result.value = response
            } catch (e: Exception) {
                _result.value = Result.Error(e, e.message ?: "Unknown error")
            }
        }
    }

}