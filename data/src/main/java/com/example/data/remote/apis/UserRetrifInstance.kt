package com.example.data.remote.apis

import com.example.data.remote.ProductApiService
import com.example.data.remote.UserDataApiService
import com.example.data.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRetrofitInstance {

    private val userRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userApi: UserDataApiService by lazy {
        userRetrofit.create(UserDataApiService::class.java)
    }
    val medicineApi: ProductApiService by lazy {
        userRetrofit.create(ProductApiService::class.java)
    }
}