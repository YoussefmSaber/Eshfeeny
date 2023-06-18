package com.example.eshfeenygraduationproject.eshfeeny.alarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.AlarmRepoImpl
import com.example.data.repository.PharmacyRepoImpl
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.PharmacyViewModel

class AlarmViewModelFactory(private val repository: AlarmRepoImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmViewModel(repository) as T
    }
}