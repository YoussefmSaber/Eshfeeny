package com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.local.db.user.UserDatabase
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.insuranceCard.InsuranceCardPatchItem
import com.example.domain.entity.insuranceCard.InsuranceCardResponse
import com.example.domain.entity.insuranceCard.InsuranceCardX
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
            val res = repository.getUserData()
            if (res != null)
                _userData.value = res
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
                val res = repository.getInsuranceCards(userId)
                if (res != null)
                    _insuranceCardItems.value = res
            } catch (e: Exception) {
                Log.e("card", "Error fetching the insurance card items $e")
            }
        }
    }

    fun addInsuranceCard(
        userId: String,
        card: InsuranceCardX
    ) {
        viewModelScope.launch {
            try {
                Log.d("Insurance Card", card.toString())
                repository.addInsuranceCard(
                    userId,
                    InsuranceCardPatchItem(
                        imageURL = card.imageURL,
                        name = card.name,
                        nameOnCard = card.nameOnCard,
                        number = card.number
                    )
                )
            } catch (e: Exception) {
                Log.e("InsuranceCard", "Error adding InsuranceCard" + e)
            }
        }
    }

    fun updateUserData(
        userIdLocal: Int,
        userIdRemote: String,
        newUserData: UpdateUserData,
        newGender: String
    ) {
        viewModelScope.launch {
            repository.updateUserData(
                userIdRemote,
                newUserData,
                userIdLocal
            )

            repository.updateUserGender(
                userIdRemote,
                newGender,
                userIdLocal
            )
        }
    }
}