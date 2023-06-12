package com.example.data.remote.apis

import com.example.data.remote.ImageApiService
import com.example.data.remote.UserDataApiService
import com.example.data.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImageUploadApiInstance {

    private val imageUploadInstance by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.IMAGE_UPLOAD_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val imageApi by lazy {
        imageUploadInstance.create(ImageApiService::class.java)
    }
}