package com.example.data.repository

import com.example.data.remote.apis.MedicineRetrofitInstance
import com.example.domain.entity.CategoryResponse

class MedicineRepoImpl {
    suspend fun getMedicineFromRemote(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromEmsaak()
}