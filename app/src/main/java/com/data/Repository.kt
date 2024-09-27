package com.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.data.response.BlokResponse
import com.data.response.BookedResponse
import com.data.response.ListBookedResponse
import com.data.response.LoginResponse
import com.data.response.SeatStatusResponse
import com.data.response.SlotParkirResponse
import com.data.response.SlotResponse
import com.data.retrofit.ApiService
import com.ui.login.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val apiService: ApiService, private val context: Context) {

    private val userPreferences = UserPreferences(context)


    fun isTokenValid(): Boolean {
        return userPreferences.isTokenExpired()
    }

    fun clearToken() {
        userPreferences.clearUser()
    }


    suspend fun login(email: String, password: String) : Result<LoginResponse> {
        return try {
            val response = apiService.login(email, password)
            userPreferences.saveUser(response.id,response.accessToken, response.expiresIn)
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

    suspend fun bookSlot(
        platNomor: String,
        jenisMobil: String,
        idSlot: String
    ): Result<BookedResponse>
    {
        val idUser = userPreferences.getIdUser()
        return try {
            val response = apiService.bookSlot(idUser, platNomor, jenisMobil, idSlot)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error")
        }
    }



    suspend fun getSlot(id: Int): Result<SlotResponse> {
        return try {
            val response = apiService.getALlSlot(id)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error")
        }
    }

    suspend fun getSlotBlok(id: Int): Result<BlokResponse> {
        return try {
            val response = apiService.getBlokTotal(id)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error")
        }
    }

    suspend fun getSeatStatus(id: Int): Result<SeatStatusResponse> {
        return try {
            val response = apiService.getStatusDepanBelakang(id)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error")
        }
    }

    suspend fun getListBooked(): Result<ListBookedResponse> {
        return try {
            val id = userPreferences.getIdUser()
            val response = apiService.getListBook(id)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error")
        }
    }

}
