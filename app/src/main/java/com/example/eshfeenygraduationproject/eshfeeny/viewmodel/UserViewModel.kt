package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.local.db.user.UserDatabase
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.patchRequestVar.PatchProductId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepoImpl

    private val _userData: MutableLiveData<UserInfo> = MutableLiveData()
    val userData: LiveData<UserInfo>
        get() = _userData

    private val _cartItems: MutableLiveData<CartResponse> = MutableLiveData()
    val cartItems: LiveData<CartResponse>
        get() = _cartItems


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

    fun getUserCartItems(userId: String) {
        viewModelScope.launch {
            try {
                _cartItems.value = repository.getUserCartItems(userId)
            } catch (e: Exception) {
                Log.e("cart", "Error fetching the cart items")
            }
        }
    }

    fun addProductToCart(
        userId: String,
        productId: PatchProductId
    ) {
        viewModelScope.launch {
            try {
                repository.addProductToCart(userId, productId)
            } catch (e: Exception) {
                Log.e("cart", "Error adding product to cart")
            }
        }
    }

    fun removeProductFromCart(
        userId: String,
        productId: PatchProductId
    ) {
        viewModelScope.launch {
            try {
                repository.removeProductFromCart(userId, productId)
            } catch (e: Exception) {
                Log.e("cart", "Error removing product from cart")
            }
        }
    }

    fun incrementProductNumberInCart(
        userId: String,
        productId: String
    ) {
        viewModelScope.launch {
            try {
                repository.incrementProductNumberInCart(userId, productId)
            } catch (e: Exception) {
                Log.e("cart", "Error incrementing product number")
            }
        }
    }

    fun decrementProductNumberInCart(
        userId: String,
        productId: String
    ) {
        viewModelScope.launch {
            try {
                repository.decrementProductNumberInCart(userId, productId)
            } catch (e: Exception) {
                Log.e("cart", "Error decrementing product number")
            }
        }
    }

    fun getNumberOfItemInCart(
        userId: String,
        productId: String
    ) {
        viewModelScope.launch {
            try {
                repository.getNumberOfItemInCart(userId, productId)
            } catch (e: Exception) {
                Log.e("cart", "Error getting the product number")
            }
        }
    }
}