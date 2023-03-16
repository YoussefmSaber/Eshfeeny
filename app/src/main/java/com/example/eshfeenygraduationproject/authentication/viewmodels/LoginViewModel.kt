package com.example.eshfeenygraduationproject.authentication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.CheckEmailResponse
import com.example.domain.entity.UserResponse
import com.example.domain.entity.VerifyLoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: UserRepoImpl): ViewModel() {

    val verifyUserLogin: MutableLiveData<Response<UserResponse>?> = MutableLiveData()
    val checkEmailResponse: MutableLiveData<CheckEmailResponse> = MutableLiveData()

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
}