package com.ui.detailbooked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.Result
import com.data.response.DetailBookedResponse
import com.data.response.ListBookedResponse
import kotlinx.coroutines.launch

class DetailBookedViewModel(val repository: Repository): ViewModel() {

    private val _bookedResponse = MutableLiveData<Result<DetailBookedResponse>>()
    val bookedResponse : LiveData<Result<DetailBookedResponse>> = _bookedResponse

    fun getBooked(id: Int){
        viewModelScope.launch {
            _bookedResponse.value = Result.Loading
            val result = repository.getDetailBooked(id)
            _bookedResponse.value = result
        }
    }
}