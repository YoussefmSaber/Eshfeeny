package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MedicineRepoImpl
import com.example.domain.entity.CategoryResponse
import kotlinx.coroutines.launch


class MedicineViewModel(
   private val repoImpl: MedicineRepoImpl
):ViewModel() {
    private val _categories = MutableLiveData<CategoryResponse>()
    val categories: LiveData<CategoryResponse>
    get() = _categories


    fun getMedicine(){
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineFromRemote()
                _categories.value = response
                Log.i("mvvm sh8aal",toString())
            }catch (e:Exception){
                // handle error
                Log.e(TAG, "Error fetching urls", e)
            }

        }

    }
}