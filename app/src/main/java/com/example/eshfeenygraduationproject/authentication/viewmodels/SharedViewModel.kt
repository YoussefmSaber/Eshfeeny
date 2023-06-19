package com.example.eshfeenygraduationproject.authentication.viewmodels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.local.db.user.UserDatabase
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.CheckEmailResponse
import com.example.domain.entity.CreateUser
import com.example.domain.entity.SendToCheckEmail
import com.example.domain.entity.VerifyCodeResponse
import com.example.domain.entity.VerifyLoginResponse
import com.example.domain.entity.patchRequestVar.ChangePassword
import com.example.domain.entity.patchresponse.PatchRequestResponse
import com.example.eshfeenygraduationproject.eshfeeny.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepoImpl

    private val _userData: MutableLiveData<UserInfo> = MutableLiveData()
    val userData: LiveData<UserInfo>
        get() = _userData

    private val _verifyUserLogin: MutableLiveData<Response<UserInfo>?> = MutableLiveData()
    val verifyUserLogin: LiveData<Response<UserInfo>?>
        get() = _verifyUserLogin

    private val _loadingToLogin: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingToLogin: LiveData<Boolean>
        get() = _loadingToLogin

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()

        repository = UserRepoImpl(userDao)

        viewModelScope.launch {
            Log.i("test viewModel", _loadingToLogin.value.toString())
            val userData = repository.getUserData()
            if (userData.email != null) {
                _userData.value = userData
                val userCredentials = VerifyLoginResponse(
                    _userData.value?.email!!,
                    _userData.value?.password!!
                )
                val res = repository.verifyLogin(userCredentials)
                if (res != null) {
                    _verifyUserLogin.value = res
                    _loadingToLogin.value = true
                    Log.i("test", _loadingToLogin.value.toString())
                    val intent = Intent(getApplication(), MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    getApplication<Application>().startActivity(intent)
                }
            }
            _loadingToLogin.value = true
        }
    }


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

    private val _updatePassword: MutableLiveData<PatchRequestResponse> = MutableLiveData()
    val updatePassword: LiveData<PatchRequestResponse>
        get() = _updatePassword

    fun verifyLogin(
        userData: VerifyLoginResponse
    ) {
        viewModelScope.launch {
            val response = repository.verifyLogin(userData)
            Log.i(
                "Login ViewModel",
                "${response.body()} + ${response.code()} + ${response.errorBody()}"
            )
            _verifyUserLogin.value = response
        }
    }

    fun checkEmailExist(email: SendToCheckEmail) {
        viewModelScope.launch {
            val response = repository.checkEmail(email)
            Log.i("Error", response.body().toString())
            response.let {
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
            val response = repository.updateUserPasswordNotLogin(id, newPassword)
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
}