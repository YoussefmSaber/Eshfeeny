package com.example.data.repository

import com.example.data.remote.apis.MedicineRetrofitInstance
import com.example.domain.entity.CategoryResponse

class MedicineRepoImpl {
    //get medicine امساك
    suspend fun getMedicineFromRemoteForEmsaak(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromEmsaak()

    //get medicine كحه
    suspend fun getMedicineFromRemoteForKo7aa(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromKo7aa()

    //get medicine مغص
    suspend fun getMedicineFromRemoteForM8aas(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromM8aas()

}