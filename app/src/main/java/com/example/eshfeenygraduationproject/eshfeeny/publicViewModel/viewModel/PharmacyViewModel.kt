package com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.PharmacyRepoImpl
import com.example.domain.entity.pharmacyResponse.PharmacyResponse
import com.example.domain.entity.pharmacySendRequest.FindNearestPharmacy
import kotlinx.coroutines.launch

class PharmacyViewModel(private val repo: PharmacyRepoImpl) : ViewModel() {

    private val _allPharmacies = MutableLiveData<PharmacyResponse>()
    val allPharmacies: LiveData<PharmacyResponse>
        get() = _allPharmacies

    fun availablePharmacies(
        listProducts: FindNearestPharmacy
    ) {
        viewModelScope.launch {
            try {
                val pharmacies = repo.availablePharmacies(listProducts)
                if (pharmacies != null)
                    _allPharmacies.value = pharmacies
            } catch (e: Exception) {
                Log.d("Getting Pharmacies", "Failed to get Pharmacies $e")
            }
        }
    }
}