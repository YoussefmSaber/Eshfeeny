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

    //get medicine الحموضة و سوء الهضم
    suspend fun getMedicineFromRemoteFor7modaAndSo2Hadm(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFrom7modaAndSo2Hadm()

    //get medicine الفيتامينات و المكملات الغذائية
    suspend fun getMedicineFromRemoteForVetamenAndMa2kolat(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromVetamenAndMa2kolat()

    //get medicine تقوية المناعة
    suspend fun getMedicineFromRemoteForT2wyaaElmna3a(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromT2wyaaElmna3a()

    //get medicine مسكنات
    suspend fun getMedicineFromRemoteForMosknaat(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromMosknaat()

    //get medicine مضادات حيوية
    suspend fun getMedicineFromRemoteForModat7aywee(): CategoryResponse =
        MedicineRetrofitInstance.medicineApi.getMedicineFromModat7aywee()
}