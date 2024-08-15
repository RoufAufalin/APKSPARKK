package com.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.data.response.LoginResponse
import com.data.response.SlotParkirResponse
import com.data.response.SlotResponse
import com.data.retrofit.ApiService
import com.ui.login.Login

class Repository(private val apiService: ApiService, private val context: Context) {

    private val userPreferences = UserPreferences(context)

    fun isTokenValid(): Boolean {
        return userPreferences.isTokenExpired()
    }

    fun clearToken() {
        userPreferences.clearUser()
    }

//    fun login(email: String, pw: String): LiveData<Result<LoginResponse>> = liveData {
//        emit(Result.Loading)
//        try {
//            val response = apiService.login(email, pw)
//            userPreferences.setUserPreferences(Username(tokenName = response.accessToken))
//            emit(Result.Success(response))
//        } catch (e: Exception) {
//            Log.e("Repository", "Login error: ${e.message}", e)
//            emit(Result.Error(e.message.toString()))
//        }
//    }

    suspend fun login(email: String, password: String) : Result<LoginResponse> {
        return try {
            val response = apiService.login(email, password)
            userPreferences.saveUser(response.accessToken, response.expiresIn)
            Log.d("Repository", "Response received: $response")
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error")
        }
    }

    suspend fun register(name: String, email: String, password: String, address: String, noHp: String) : Result<LoginResponse> {
        return try {
            val response = apiService.register(name, email, password, address, noHp)
            Log.d("Repository", "Response received: $response")
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error")
        }
    }


    fun logout(){
        userPreferences.clearUser()
    }

    fun bookSlot(
        platNomor: String,
        namaPemesan: String,
        jenisMobil: String,
        idSlot: String
    ) = apiService.bookSlot(platNomor, namaPemesan, jenisMobil, idSlot)



//    fun getSlotParkir(): LiveData<Result<SlotResponse>> = liveData {
//        emit(Result.Loading)
//        try {
//            val response = apiService.SlotAll()
//            Log.d("Repository", "Response received: $response")
//            emit(Result.Success(response))
//        } catch (e: Exception) {
//            Log.e("Repository", "Error loading slot parkir: ${e.message}", e)
//            emit(Result.Error(e, e.message ?: "Failed to load data"))
//        }
//    }

//    suspend fun login(email: String, password: String) : Result<LoginResponse> {
//        return try {
//            val response = apiService.login(email, password)
//            userPreferences.saveUser(response.accessToken, response.expiresIn)
//            Log.d("Repository", "Response received: $response")
//            Result.Success(response)
//        } catch (e: Exception) {
//            Result.Error(e, e.message ?: "Unknown error")
//        }
//    }
    suspend fun getSlot(): Result<SlotResponse> {
        return try {
            val response = apiService.getALlSlot()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error")
        }
    }

}
