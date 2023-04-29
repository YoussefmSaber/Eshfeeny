package com.example.eshfeenygraduationproject.eshfeeny.publicViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.local.db.user.UserDatabase
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.UserRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepoImpl

    private val _userData: MutableLiveData<UserInfo> = MutableLiveData()
    val userData: LiveData<UserInfo>
        get() = _userData


    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepoImpl(userDao)

        viewModelScope.launch {
            _userData.value = repository.getUserData()
        }
    }

    fun deleteUserFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserData()
        }
    }
}