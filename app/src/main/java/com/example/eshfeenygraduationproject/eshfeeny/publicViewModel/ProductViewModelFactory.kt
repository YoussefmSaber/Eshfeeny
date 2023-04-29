package com.example.eshfeenygraduationproject.eshfeeny.publicViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl

class ProductViewModelFactory(private val repository: ProductRepoImpl): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repository) as T
    }
}