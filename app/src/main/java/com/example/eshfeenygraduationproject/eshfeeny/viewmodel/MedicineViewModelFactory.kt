package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.MedicineRepoImpl

class MedicineViewModelFactory(private val repository: MedicineRepoImpl): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MedicineViewModel(repository) as T
    }
}