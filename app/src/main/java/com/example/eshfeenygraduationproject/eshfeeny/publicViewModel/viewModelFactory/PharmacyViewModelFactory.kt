package com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.PharmacyRepoImpl
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.PharmacyViewModel

class PharmacyViewModelFactory(private val repository: PharmacyRepoImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PharmacyViewModel(repository) as T
    }
}