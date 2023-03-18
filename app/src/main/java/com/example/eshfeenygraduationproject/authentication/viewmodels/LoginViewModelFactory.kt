package com.example.eshfeenygraduationproject.authentication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.UserRepoImpl

class LoginViewModelFactory(private val repository: UserRepoImpl): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}