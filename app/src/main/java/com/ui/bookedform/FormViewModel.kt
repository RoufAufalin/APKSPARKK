package com.ui.bookedform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.Result
import com.data.response.BookedResponse
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormViewModel(private val repository: Repository) : ViewModel() {
    private val _bookingResponse = MutableLiveData<Result<BookedResponse>>()
    val bookingResponse: LiveData<Result<BookedResponse>> = _bookingResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage



    fun bookSlot(
        platNomor: String,
        jenisMobil: String,
        idSlot: String
    ) {
        _loading.value = true
        viewModelScope.launch {
            _bookingResponse.value = Result.Loading
            val result = repository.bookSlot(platNomor, jenisMobil, idSlot)
            _bookingResponse.value = result
        }

    }


    fun parseErrorMessage(errorBody: String?): String {
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.optString("pesan", "Unknown error")
        } catch (e: JSONException) {
            "Error parsing error message"
        }
    }
}

