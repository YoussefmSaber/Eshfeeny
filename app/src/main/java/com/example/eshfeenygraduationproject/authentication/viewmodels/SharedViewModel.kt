package com.example.eshfeenygraduationproject.authentication.viewmodels

import android.util.Log
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

    private val _verifyNewAccountResponse: MutableLiveData<VerifyCodeResponse> = MutableLiveData()
    val verifyNewAccountResponse: LiveData<VerifyCodeResponse>
        get() = _verifyNewAccountResponse

    val _emailFound: MutableLiveData<Response<CheckEmailResponse>> = MutableLiveData()
    val emailFound: LiveData<Response<CheckEmailResponse>>
        get() = _emailFound

    private val _areBothSame: MutableLiveData<Boolean> = MutableLiveData()
    val areBothSame: LiveData<Boolean>
        get() = _areBothSame


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

    fun checkEmailExist(email: String) {
        viewModelScope.launch {
            val response = repository.checkEmail(email)
            _emailFound.value = response
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

    fun verifyNewUser(email: String) {
        viewModelScope.launch {
            val response = repository.verifySignup(email)
            _verifyNewAccountResponse.value = response
        }
    }

    fun areCodesTheSame(
        inputCode: String
    ) {
        viewModelScope.launch {
            val same = inputCode == _verifyNewAccountResponse.value?.code
            _areBothSame.value = same
            Log.i("areCodesTheSame: ", _verifyNewAccountResponse.value?.code.toString())
            Log.i("Verify code: ", _areBothSame.value.toString())
        }
    }
}