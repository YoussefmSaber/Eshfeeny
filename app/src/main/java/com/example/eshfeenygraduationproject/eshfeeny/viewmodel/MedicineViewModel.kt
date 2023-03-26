package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

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
    private val _categories_Emsaak = MutableLiveData<CategoryResponse>()
    val categories_Emsaak: LiveData<CategoryResponse>
        get() = _categories_Emsaak
    private val _categories_Ko7aa = MutableLiveData<CategoryResponse>()
    val categories_Ko7aa: LiveData<CategoryResponse>
        get() = _categories_Ko7aa
    private val _categories_M8aas = MutableLiveData<CategoryResponse>()
    val categories_M8aas: LiveData<CategoryResponse>
        get() = _categories_M8aas


    fun getMedicineForEmsaak() {
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemoteForEmsaak()
                _categories_Emsaak.value = response
                Log.i("mvvm sh8aal", toString())
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
                Log.i("mvvm sh8aal", toString())
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
                Log.i("mvvm sh8aal", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls M8ass", e)
            }
        }
    }

}