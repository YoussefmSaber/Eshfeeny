package com.example.eshfeenygraduationproject.authentication.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.*
import kotlinx.coroutines.launch
import retrofit2.Response

class SharedViewModel(private val repository: UserRepoImpl) : ViewModel() {

    val verifyUserLogin: MutableLiveData<Response<UserResponse>?> = MutableLiveData()
    val checkEmailResponse: MutableLiveData<CheckEmailResponse> = MutableLiveData()
    val createUserResponse: MutableLiveData<Response<NewUserResponse>> = MutableLiveData()
    var emailAlreadyExists: String = ""

    fun checkEmail(email: String) {
        viewModelScope.launch {
            val response = repository.checkEmail(email)
            checkEmailResponse.value = response
        }
    }

    fun verifyLogin(
        userData: VerifyLoginResponse
    ) {
        viewModelScope.launch {
            val response = repository.verifyLogin(userData)
            if (response.isSuccessful)
                verifyUserLogin.value = response
            else
                verifyUserLogin.value = null
        }
    }

    fun createNewUser(
        newUser: CreateUser
    ) {
        viewModelScope.launch {
            val response = repository.createNewUser(newUser)
            createUserResponse.value = response
        }
    }
}