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

    private val _result2 = MutableLiveData<Result<SlotResponse>>()
    val result2 : LiveData<Result<SlotResponse>> = _result2


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> =  _isLoading


    fun getSlotParkir(id : Int) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = repository.getSlot(id)
                when (id) {
                    1 -> _result.value = response
                    2 -> _result2.value = response
                }
                _result2.value = response
            } catch (e: Exception) {
                _result.value = Result.Error(e, e.message ?: "Unknown error")
            }
        }
    }

}