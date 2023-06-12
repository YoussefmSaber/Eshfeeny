package com.example.data.remote

import com.example.domain.entity.imageResponse.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApiService {

    @Multipart
    @POST("1/upload")
    suspend fun uploadImage(
        @Part
        key: MultipartBody.Part,
        @Part
        image: MultipartBody.Part
    ): ImageResponse

    @POST("imageSearch/")
    suspend fun getImageData(
        @Body
        imageURL: String
    )
}