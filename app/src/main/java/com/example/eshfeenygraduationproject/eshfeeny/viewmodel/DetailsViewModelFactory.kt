package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl

class DetailsViewModelFactory(private val repository: ProductRepoImpl): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository) as T
    }
}