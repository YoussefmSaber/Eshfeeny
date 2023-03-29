package com.example.eshfeenygraduationproject.authentication.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.data.local.db.user.UserDatabase
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepoImpl

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()

        repository = UserRepoImpl(userDao)
    }

    private val _userData: MutableLiveData<UserInfo> = MutableLiveData()
    val userData: LiveData<UserInfo>
    get() = _userData


    private val _verifyUserLogin: MutableLiveData<Response<UserInfo>> = MutableLiveData()
    val verifyUserLogin: LiveData<Response<UserInfo>>
        get() = _verifyUserLogin

    private val _createUserResponse: MutableLiveData<Response<UserInfo>> = MutableLiveData()
    val createUserResponse: LiveData<Response<UserInfo>>
        get() = _createUserResponse

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
            _verifyUserLogin.value = response
        }
    }

    fun checkEmailExist(email: SendToCheckEmail) {
        viewModelScope.launch {
            val response = repository.checkEmail(email)
            Log.i("Error", response.body().toString())
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
            Log.i("code", response.toString())
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

    fun addUserToDatabase(
        userData: UserInfo
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserDataToDatabase(userData)
            Log.i("DB", userData.toString())
        }
    }

    fun deleteUserFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserData()
        }
    }

    fun getUserData(){
        viewModelScope.launch {
            _userData.value = repository.getUserData()
        }
    }
}