package com.example.eshfeenygraduationproject.authentication.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.*
import kotlinx.coroutines.launch
import retrofit2.Response

class SharedViewModel(private val repository: UserRepoImpl) : ViewModel() {

    private val _verifyUserLogin: MutableLiveData<Response<UserResponse>?> = MutableLiveData()
    val verifyUserLogin: LiveData<Response<UserResponse>?>
        get() = _verifyUserLogin

    private val _createUserResponse: MutableLiveData<Response<NewUserResponse>> = MutableLiveData()
    val createUserResponse: LiveData<Response<NewUserResponse>>
        get() = _createUserResponse


    fun verifyLogin(
        userData: VerifyLoginResponse
    ) {
        viewModelScope.launch {
            val response = repository.verifyLogin(userData)
            if (response.isSuccessful)
                _verifyUserLogin.value = response
            else
                _verifyUserLogin.value = null
        }
    }

    fun createNewUser(
        newUser: CreateUser
    ) {
        viewModelScope.launch {
            val response = repository.createNewUser(newUser)
            _createUserResponse.value = response
        }
    }

    fun verifySignup(
        email: String
    ) {
        viewModelScope.launch {
            // TODO: Implement this function
        }
    }
}