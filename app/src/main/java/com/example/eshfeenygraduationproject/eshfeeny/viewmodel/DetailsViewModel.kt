package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.CategoryResponse

class DetailsViewModel: ViewModel() {
    private val _medicine = MutableLiveData<CategoryResponse>()
    val medicine: LiveData<CategoryResponse>
        get() = _medicine
    fun setMedicine(medicine:CategoryResponse){
        _medicine.value = medicine
    }
}