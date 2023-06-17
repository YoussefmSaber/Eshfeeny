package com.example.data.repository

import com.example.data.remote.apis.EshfeenyApiInstance
import com.example.domain.entity.pharmacyResponse.PharmacyResponse
import com.example.domain.entity.pharmacySendRequest.FindNearestPharmacy

class PharmacyRepoImpl {

    suspend fun getAllPharmacies(): PharmacyResponse =
        EshfeenyApiInstance.pharmacyApi.getAllPharmacies()

    suspend fun availablePharmacies(listProducts: FindNearestPharmacy) =
        EshfeenyApiInstance.pharmacyApi.availablePharmacies(listProducts)
}