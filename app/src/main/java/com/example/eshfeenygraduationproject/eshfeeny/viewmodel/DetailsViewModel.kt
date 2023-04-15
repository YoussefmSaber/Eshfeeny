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
import com.example.domain.entity.CategoryResponseItem
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsViewModel(
    private val repoImpl: MedicineRepoImpl
): ViewModel() {
    private val _medicine = MutableLiveData<Response<CategoryResponseItem>>()
    val medicine: LiveData<Response<CategoryResponseItem>>
        get() = _medicine
    @SuppressLint("LongLogTag")
    fun setMedicine(id: String){
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineDetailsFromRemote(id)
                _medicine.value = response
                Log.i("mvvm sh8aal All Medicines in details fragment ", toString())
            }catch (e:Exception){
                Log.e(TAG, "Error in MVVM details in all medicines", e)
            }
        }

    }
}