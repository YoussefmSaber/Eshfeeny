package com.example.data.remote.apis

import com.example.data.remote.Service.AlarmApiService
import com.example.data.remote.Service.PharmaciesApiService
import com.example.data.remote.Service.ProductApiService
import com.example.data.remote.Service.UserDataApiService
import com.example.data.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EshfeenyApiInstance {

    private val userRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.ESHFEENY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userApi: UserDataApiService by lazy {
        userRetrofit.create(UserDataApiService::class.java)
    }
    val productApi: ProductApiService by lazy {
        userRetrofit.create(ProductApiService::class.java)
    }
    val pharmacyApi: PharmaciesApiService by lazy {
        userRetrofit.create(PharmaciesApiService::class.java)
    }
    val alarmApi: AlarmApiService by lazy {
        userRetrofit.create(AlarmApiService::class.java)
    }
}