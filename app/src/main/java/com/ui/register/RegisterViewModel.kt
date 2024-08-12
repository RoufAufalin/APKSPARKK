package com.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.Result
import com.data.response.LoginResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository): ViewModel() {
    private val _registResult = MutableLiveData<Result<LoginResponse>>()
    val registResult: LiveData<Result<LoginResponse>> = _registResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> =  _isLoading

    fun register (name: String, email: String, pass: String, address: String, phone: String) {
        viewModelScope.launch {
            _registResult.value = Result.Loading
            val result = repository.register(name, email, pass, address, phone)
            _registResult.value = result
        }
    }
}