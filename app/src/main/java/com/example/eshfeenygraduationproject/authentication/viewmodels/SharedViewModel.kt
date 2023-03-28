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

    private val _emailFound: MutableLiveData<Response<CheckEmailResponse>> = MutableLiveData()
    val emailFound: LiveData<Response<CheckEmailResponse>>
        get() = _emailFound

    private val _areBothSame: MutableLiveData<Boolean> = MutableLiveData()
    val areBothSame: LiveData<Boolean>
        get() = _areBothSame

    private val _updatePassword: MutableLiveData<PasswordChangeResponse> = MutableLiveData()
    val updatePassword: LiveData<PasswordChangeResponse>
        get() = _updatePassword

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

    fun checkEmailExist(email: SendToCheckEmail) {
        viewModelScope.launch {
            val response = repository.checkEmail(email)
            response?.let {
                _emailFound.value = it
                Log.i("code", it.body()?._id.toString())
            }
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

    fun verifyCode(email: String) {
        viewModelScope.launch {
            val response = repository.verifyCode(email)
            _verifyNewAccountResponse.value = response
        }
    }

    fun areCodesTheSame(
        inputCode: String
    ) {
        viewModelScope.launch {
            val same = inputCode == _verifyNewAccountResponse.value?.code
            _areBothSame.value = same
        }
    }

    fun updateUserPassword(
        id: String,
        newPassword: ChangePassword
    ) {
        viewModelScope.launch {
            val response = repository.updateUserPassword(id, newPassword)
            _updatePassword.value = response
        }
    }
}