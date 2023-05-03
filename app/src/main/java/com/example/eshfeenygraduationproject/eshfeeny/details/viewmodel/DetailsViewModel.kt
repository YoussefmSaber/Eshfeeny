package com.example.eshfeenygraduationproject.eshfeeny.details.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.product.ProductResponse
import kotlinx.coroutines.launch

class DetailsViewModel(private val repo: ProductRepoImpl) : ViewModel() {


}