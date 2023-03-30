package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.db.user.UserDatabase
import com.example.data.repository.UserRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepoImpl

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()

        repository = UserRepoImpl(userDao)
    }

    fun deleteUserFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserData()
        }
    }
}