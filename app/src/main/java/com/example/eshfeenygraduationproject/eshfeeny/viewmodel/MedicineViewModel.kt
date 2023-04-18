package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MedicineRepoImpl
import com.example.domain.entity.CategoryResponse
import com.example.domain.entity.patchRequestVar.AddToFavorites
import com.example.domain.entity.patchresponse.PatchRequestResponse
import kotlinx.coroutines.launch
import retrofit2.Response


class MedicineViewModel(
    private val repoImpl: MedicineRepoImpl
) : ViewModel() {

    private val _categories_medicines = MutableLiveData<Response<CategoryResponse>>()
    val category_medicines: LiveData<Response<CategoryResponse>>
        get() = _categories_medicines

    //Categories For Emsaak
    private val _categories_Emsaak = MutableLiveData<CategoryResponse>()
    val categories_Emsaak: LiveData<CategoryResponse>
        get() = _categories_Emsaak

    //Categories For Ko7aa
    private val _categories_Ko7aa = MutableLiveData<CategoryResponse>()
    val categories_Ko7aa: LiveData<CategoryResponse>
        get() = _categories_Ko7aa

    //Categories For M8aas
    private val _categories_M8aas = MutableLiveData<CategoryResponse>()
    val categories_M8aas: LiveData<CategoryResponse>
        get() = _categories_M8aas

    //Categories For كل الادويه
    private val _categoriesAllMedicines = MutableLiveData<CategoryResponse>()
    val categoriesAllMedicines: LiveData<CategoryResponse>
        get() = _categoriesAllMedicines

    private val _medicineToFavorites = MutableLiveData<PatchRequestResponse>()
    val medicineToFavorite: LiveData<PatchRequestResponse>
        get() = _medicineToFavorites

    private val _favoriteProducts = MutableLiveData<CategoryResponse>()
    val favoriteProducts: LiveData<CategoryResponse>
        get() = _favoriteProducts

    fun getMedicinesFromRemote(medicine: String) {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicinesFromRemote(medicine)
                _categories_medicines.value = response
                Log.i("Chip Click Test", response.toString())
            } catch (e: Exception) {
                Log.e(TAG, "ERROR FETCHING URLS " + e)
            }
        }
    }

    fun getMedicineForEmsaak() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForEmsaak()
                _categories_Emsaak.value = response
                Log.i("mvvm sh8aal Emsaak", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls Emsaak", e)
            }
        }
    }

    fun getMedicineForKo7aa() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForKo7aa()
                _categories_Ko7aa.value = response
                Log.i("mvvm sh8aal Ko7aa ", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls Ko7aa", e)
            }
        }
    }

    fun getMedicineForM8aas() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForM8aas()
                _categories_M8aas.value = response
                Log.i("mvvm sh8aal M8aas", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls M8ass", e)
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun getMedicineForAllMedicines() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForAllMedicines()
                _categoriesAllMedicines.value = response
                Log.i("Chip Click Test", _categoriesAllMedicines.value.toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls AllMedicines", e)
            }
        }
    }

    fun addMedicineToFavorites(
        userId: String,
        productId: AddToFavorites
    ) {
        viewModelScope.launch {
            try {
                val response = repoImpl.addMedicineToFavorites(userId, productId)
                _medicineToFavorites.value = response
            } catch (e: Exception) {
                Log.e("Favorite", "Error adding medicine to favorites" + e)
            }
        }
    }

    fun getFavoriteProducts(
        userId: String
    ) {
        viewModelScope.launch {
            try {
                val response = repoImpl.getFavoriteProducts(userId)
                _favoriteProducts.value = response
            } catch (e: Exception) {
                Log.e("error", "Error fetching Favorite Products")
            }
        }
    }
}