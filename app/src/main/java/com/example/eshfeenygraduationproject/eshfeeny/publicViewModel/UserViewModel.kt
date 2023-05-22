package com.example.eshfeenygraduationproject.eshfeeny.publicViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.local.db.user.UserDatabase
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.insuranceCard.InsuranceCardResponse
import com.example.domain.entity.patchRequestVar.UpdateUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepoImpl

    private val _userData: MutableLiveData<UserInfo> = MutableLiveData()
    val userData: LiveData<UserInfo>
        get() = _userData
    private val _insuranceCardItems: MutableLiveData<InsuranceCardResponse> = MutableLiveData()
    val insuranceCardItems: LiveData<InsuranceCardResponse>
        get() = _insuranceCardItems

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

    fun getInsuranceCards(userId: String) {
        viewModelScope.launch {
            try {
                _insuranceCardItems.value = repository.getInsuranceCards(userId)
            } catch (e: Exception) {
                Log.e("card", "Error fetching the insurance card items $e")
            }
        }
    }

    fun updateUserData(
        userIdLocal: Int,
        userIdRemote: String,
        newUserData: UpdateUserData,
        newGender: String
    ){
        viewModelScope.launch {
            repository.updateUserData(
                userIdRemote,
                newUserData,
                userIdLocal
            )
        }
    }
}