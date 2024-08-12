package com.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.data.response.LoginResponse
import com.data.response.SlotParkirResponse
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

    suspend fun register(name: String, password: String, email: String) : Result<LoginResponse> {
        return try {
            val response = apiService.login(email, password)
            userPreferences.saveUser(response.accessToken, response.expiresIn)
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
        tanggalMasuk: String,
        tanggalKeluar: String,
        idSlot: String
    ) = apiService.bookSlot(platNomor, namaPemesan, jenisMobil, tanggalMasuk, tanggalKeluar, idSlot)



    fun getSlotParkir(): LiveData<Result<SlotParkirResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.SlotAll()
            Log.d("Repository", "Response received: $response")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("Repository", "Error loading slot parkir: ${e.message}", e)
            emit(Result.Error(e, e.message ?: "Failed to load data"))
        }
    }

}
