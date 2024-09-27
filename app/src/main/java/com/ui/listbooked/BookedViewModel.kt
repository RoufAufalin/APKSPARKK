package com.ui.listbooked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.response.ListBookedResponse
import com.data.Result
import kotlinx.coroutines.launch

class BookedViewModel(private val repository: Repository): ViewModel() {

    private val _bookedResponse = MutableLiveData<Result<ListBookedResponse>>()
    val bookedResponse : LiveData<Result<ListBookedResponse>> = _bookedResponse

    fun getListBooked(){
        viewModelScope.launch {
            _bookedResponse.value = Result.Loading
            val result = repository.getListBooked()
            _bookedResponse.value = result
        }
    }
}