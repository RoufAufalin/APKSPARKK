package com.data

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Throwable, val pesan: String) : Result<Nothing>()
    data class LoadingState(val progress: Int) // Separate data class for loading


    object Loading : Result<Nothing>() {
        fun toLoadingState(progress: Int): LoadingState {
            return LoadingState(progress)
        }
    }
}
