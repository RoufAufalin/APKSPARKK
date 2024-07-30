package com.di

import android.content.Context
import com.data.Repository
import com.data.retrofit.ApiConfig

object Injection {
    fun repositoryProvide(context: Context): Repository {
        val apiService = ApiConfig.getApiService(context)
        return Repository(apiService, context)
    }
}
