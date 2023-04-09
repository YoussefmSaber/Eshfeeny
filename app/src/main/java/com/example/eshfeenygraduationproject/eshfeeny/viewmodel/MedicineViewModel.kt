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
import kotlinx.coroutines.launch


class MedicineViewModel(
    private val repoImpl: MedicineRepoImpl
) : ViewModel() {
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

    //Categories For 7modaa and So2 Hadm
    private val _categories_7modaAndSo2Hadm = MutableLiveData<CategoryResponse>()
    val categories_7modaAndSo2Hadm: LiveData<CategoryResponse>
        get() = _categories_7modaAndSo2Hadm

    //Categories For Vetamen And Ma2kolat
    private val _categories_VetamenAndMa2kolat = MutableLiveData<CategoryResponse>()
    val categories_VetamenAndMa2kolat: LiveData<CategoryResponse>
        get() = _categories_VetamenAndMa2kolat

    //Categories For T2wyaa Elmna3a
    private val _categories_T2wyaaElmna3a = MutableLiveData<CategoryResponse>()
    val categories_T2wyaaElmna3a: LiveData<CategoryResponse>
        get() = _categories_T2wyaaElmna3a

    //Categories For Mosknaat
    private val _categories_Mosknaat = MutableLiveData<CategoryResponse>()
    val categories_Mosknaat: LiveData<CategoryResponse>
        get() = _categories_Mosknaat

    //Categories For Modat 7aywee
    private val _categories_Modat7aywee = MutableLiveData<CategoryResponse>()
    val categories_Modat7aywee: LiveData<CategoryResponse>
        get() = _categories_Modat7aywee

    //Categories For كل الادويه
    private val _categories_AllMedicines = MutableLiveData<CategoryResponse>()
    val categories_AllMedicines: LiveData<CategoryResponse>
        get() = _categories_AllMedicines


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
    fun getMedicineFor7modaAndSo2Hadm() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteFor7modaAndSo2Hadm()
                _categories_7modaAndSo2Hadm.value = response
                Log.i("mvvm sh8aal 7moda And So2Hadm ", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls 7modaAndSo2Hadm", e)
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun getMedicineForVetamenAndMa2kolat() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForVetamenAndMa2kolat()
                _categories_VetamenAndMa2kolat.value = response
                Log.i("mvvm sh8aal Vetamen And Ma2kolat ", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls VetamenAndMa2kolat", e)
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun getMedicineForT2wyaaElmna3a() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForT2wyaaElmna3a()
                _categories_T2wyaaElmna3a.value = response
                Log.i("mvvm sh8aal T2wyaa Elmna3a", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls T2wyaaElmna3a", e)
            }
        }
    }

    fun getMedicineForMosknaat() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForMosknaat()
                _categories_Mosknaat.value = response
                Log.i("mvvm sh8aal Mosknaat ", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls Mosknaat", e)
            }
        }
    }

    fun getMedicineForModat7aywee() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForModat7aywee()
                _categories_Modat7aywee.value = response
                Log.i("mvvm sh8aal Modat7aywee", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls Modat7aywee", e)
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun getMedicineForAllMedicines() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForAllMedicines()
                _categories_AllMedicines.value = response
                Log.i("mvvm sh8aal All Medicines ", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls AllMedicines", e)
            }
        }
    }


}