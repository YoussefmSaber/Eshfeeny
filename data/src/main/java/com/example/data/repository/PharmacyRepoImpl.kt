package com.example.data.repository

import com.example.data.remote.apis.EshfeenyApiInstance
import com.example.domain.entity.pharmacyResponse.PharmacyResponse

class PharmacyRepoImpl {

    suspend fun getAllPharmacies(): PharmacyResponse =
        EshfeenyApiInstance.pharmacyApi.getAllPharmacies()
}