package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.MedicineRepoImpl

class ProductViewModelFactory(private val repository: MedicineRepoImpl): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repository) as T
    }
}