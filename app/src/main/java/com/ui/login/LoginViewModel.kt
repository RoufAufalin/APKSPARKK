package com.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.response.LoginResponse
import kotlinx.coroutines.launch
import com.data.Result

class LoginViewModel(private val Repo: Repository): ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> =  _isLoading

//    fun postLogin(email: String, pass: String) = storyRepo.login(email, pass)

    fun login (email: String, pass: String) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            val result = Repo.login(email, pass)
            _loginResult.value = result
        }
    }

    fun checkToken(): Boolean {
        return Repo.isTokenValid()
    }

    fun logout() {
        Repo.logout()
    }
}