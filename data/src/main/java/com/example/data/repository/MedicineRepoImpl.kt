package com.example.data.repository

import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.CategoryResponse
import com.example.domain.entity.CategoryResponseItem
import retrofit2.Response

class MedicineRepoImpl {
    //get medicine امساك
    suspend fun getMedicineFromRemoteForEmsaak(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromEmsaak()

    //get medicine كحه
    suspend fun getMedicineFromRemoteForKo7aa(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromKo7aa()

    //get medicine مغص
    suspend fun getMedicineFromRemoteForM8aas(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromM8aas()

    //get medicine الحموضة و سوء الهضم
    suspend fun getMedicineFromRemoteFor7modaAndSo2Hadm(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFrom7modaAndSo2Hadm()

    //get medicine الفيتامينات و المكملات الغذائية
    suspend fun getMedicineFromRemoteForVetamenAndMa2kolat(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromVetamenAndMa2kolat()

    //get medicine تقوية المناعة
    suspend fun getMedicineFromRemoteForT2wyaaElmna3a(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromT2wyaaElmna3a()

    //get medicine مسكنات
    suspend fun getMedicineFromRemoteForMosknaat(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromMosknaat()

    //get medicine مضادات حيوية
    suspend fun getMedicineFromRemoteForModat7aywee(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromModat7aywee()

    //get All Medicine
    suspend fun getMedicineFromRemoteForAllMedicines(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromAllMedicines()

    //get details for medicine
    suspend fun getMedicineDetailsFromRemote(id: String): Response<CategoryResponseItem> {
        return UserRetrofitInstance.medicineApi.getMedicineDetails(id)
    }
}