package com.example.data.remote.apis

import com.example.data.remote.MedicineDataApiService
import com.example.data.remote.UserDataApiService
import com.example.data.utils.Constants
import com.example.data.utils.Constants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object MedicineRetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val medicineRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val medicineApi: MedicineDataApiService by lazy {
        medicineRetrofit.create(MedicineDataApiService::class.java)
    }
}