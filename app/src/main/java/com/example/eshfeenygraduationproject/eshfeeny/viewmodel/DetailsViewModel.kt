package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.CategoryResponse
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    private val _medicine = MutableLiveData<CategoryResponse>()
    val medicine: LiveData<CategoryResponse>
        get() = _medicine
    fun setMedicine(medicine:CategoryResponse){
        viewModelScope.launch {
            try {
                _medicine.value = medicine
            }catch (e:Exception){
                Log.e(TAG, "Error in MVVM details", e)
            }
        }

    }
}