package com.ui.bookedform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.response.BookedResponse
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormViewModel(private val repository: Repository) : ViewModel() {
    private val _bookingResponse = MutableLiveData<BookedResponse>()
    val bookingResponse: LiveData<BookedResponse> = _bookingResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun bookSlot(
        platNomor: String,
        namaPemesan: String,
        jenisMobil: String,
        tanggalMasuk: String,
        tanggalKeluar: String,
        idSlot: String
    ) {
        _loading.value = true
        viewModelScope.launch {
            repository.bookSlot(platNomor, namaPemesan, jenisMobil, tanggalMasuk, tanggalKeluar, idSlot)
                .enqueue(object : Callback<BookedResponse> {
                    override fun onResponse(call: Call<BookedResponse>, response: Response<BookedResponse>) {
                        _loading.value = false
                        if (response.isSuccessful) {
                            _bookingResponse.value = response.body()
                        } else if (response.code() == 400) {
                            // Parse error message from response body
                            val errorResponse = response.errorBody()?.string()
                            val errorMessage = parseErrorMessage(errorResponse)
                            _errorMessage.value = errorMessage
                        }
                    }

                    override fun onFailure(call: Call<BookedResponse>, t: Throwable) {
                        _loading.value = false
                        _errorMessage.value = "An error occurred: ${t.message}"
                    }
                })
        }
    }

    private fun parseErrorMessage(errorBody: String?): String {
        // Assuming the error body is a JSON string
        // Extract the "pesan" field from the JSON response
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.optString("pesan", "Unknown error")
        } catch (e: JSONException) {
            "Error parsing error message"
        }
    }
}

