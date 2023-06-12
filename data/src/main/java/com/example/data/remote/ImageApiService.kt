package com.example.data.remote

import android.graphics.Bitmap
import com.example.data.remote.apis.ImageUploadApiInstance
import retrofit2.http.POST

interface ImageApiService {

    @POST("upload")
    suspend fun uploadImage(
        key: String,
        image: Bitmap
    ): ImageResponse {
        ImageUploadApiInstance.imageApi.uploadImage(key, image)
    }
}