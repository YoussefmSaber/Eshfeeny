package com.example.data.repository

import com.example.data.remote.apis.MedicineRetrofitInstance
import com.example.domain.entity.CategoryResponseItem
import com.example.domain.entity.CharacterResponse
import retrofit2.Response

class MedicineRepoImpl {
    suspend fun getMedicineFromRemote(): CharacterResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromEmsaak()
}