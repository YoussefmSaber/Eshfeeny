package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MedicineRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.patchresponse.PatchRequestResponse
import kotlinx.coroutines.launch
import retrofit2.Response


class MedicineViewModel(
    private val repo: MedicineRepoImpl
) : ViewModel() {

    private val _categories_medicines = MutableLiveData<Response<ProductResponse>>()
    val category_medicines: LiveData<Response<ProductResponse>>
        get() = _categories_medicines

    //Categories For Emsaak
    private val _categories_Emsaak = MutableLiveData<ProductResponse>()
    val categories_Emsaak: LiveData<ProductResponse>
        get() = _categories_Emsaak

    //Categories For Ko7aa
    private val _categories_Ko7aa = MutableLiveData<ProductResponse>()
    val categories_Ko7aa: LiveData<ProductResponse>
        get() = _categories_Ko7aa

    //Categories For M8aas
    private val _categories_M8aas = MutableLiveData<ProductResponse>()
    val categories_M8aas: LiveData<ProductResponse>
        get() = _categories_M8aas

    //Categories For كل الادويه
    private val _categoriesAllMedicines = MutableLiveData<ProductResponse>()
    val categoriesAllMedicines: LiveData<ProductResponse>
        get() = _categoriesAllMedicines

    private val _medicineToFavorites = MutableLiveData<PatchRequestResponse>()
    val medicineToFavorite: LiveData<PatchRequestResponse>
        get() = _medicineToFavorites

    private val _favoriteProducts = MutableLiveData<ProductResponse>()
    val favoriteProducts: LiveData<ProductResponse>
        get() = _favoriteProducts

    private val _deleteFavoriteProduct = MutableLiveData<PatchRequestResponse>()
    val deleteFavoriteProduct: LiveData<PatchRequestResponse>
        get() = _deleteFavoriteProduct

    private val _cartItems: MutableLiveData<CartResponse> = MutableLiveData()
    val cartItems: LiveData<CartResponse>
        get() = _cartItems

    private val _productNumber: MutableLiveData<Int> = MutableLiveData()
    val productNumber: LiveData<Int>
        get() = _productNumber

    fun getMedicinesFromRemote(medicine: String) {
        viewModelScope.launch {
            try {
                val response = repo.getMedicinesFromRemote(medicine)
                _categories_medicines.value = response
                Log.i("Chip Click Test", response.toString())
            } catch (e: Exception) {
                Log.e(TAG, "ERROR FETCHING URLS " + e)
            }
        }
    }

    fun getMedicineForEmsaak() {
        viewModelScope.launch {
            try {
                val response = repo.getMedicineFromRemoteForEmsaak()
                _categories_Emsaak.value = response
                Log.i("mvvm sh8aal Emsaak", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls Emsaak", e)
            }
        }
    }

    fun getMedicineForKo7aa() {
        viewModelScope.launch {
            try {
                val response = repo.getMedicineFromRemoteForKo7aa()
                _categories_Ko7aa.value = response
                Log.i("mvvm sh8aal Ko7aa ", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls Ko7aa", e)
            }
        }
    }

    fun getMedicineForM8aas() {
        viewModelScope.launch {
            try {
                val response = repo.getMedicineFromRemoteForM8aas()
                _categories_M8aas.value = response
                Log.i("mvvm sh8aal M8aas", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls M8ass", e)
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun getMedicineForAllMedicines() {
        viewModelScope.launch {
            try {
                val response = repo.getMedicineFromRemoteForAllMedicines()
                _categoriesAllMedicines.value = response
                Log.i("Chip Click Test", _categoriesAllMedicines.value.toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls AllMedicines", e)
            }
        }
    }

    fun addMedicineToFavorites(
        userId: String,
        productId: PatchProductId
    ) {
        viewModelScope.launch {
            try {
                val response = repo.addMedicineToFavorites(userId, productId)
                _medicineToFavorites.value = response
            } catch (e: Exception) {
                Log.e("Favorite", "Error adding medicine to favorites" + e)
            }
        }
    }

    fun getFavoriteProducts(
        userId: String
    ) {
        viewModelScope.launch {
            try {
                _favoriteProducts.value = repo.getFavoriteProducts(userId)
            } catch (e: Exception) {
                Log.e("error", "Error fetching Favorite Products")
            }
        }
    }

    fun deleteFavoriteProduct(
        userId: String,
        productId: String
    ) {
        viewModelScope.launch {
            try {
                val response = repo.deleteFavoriteProduct(userId, productId)
                _deleteFavoriteProduct.value = response
            } catch (e: Exception) {
                Log.i("delete Favorite", "Couldn't delete the favorite item")
            }
        }
    }

    fun getUserCartItems(userId: String) {
        viewModelScope.launch {
            try {
                _cartItems.value = repo.getUserCartItems(userId)
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
                repo.addProductToCart(userId, productId)
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
                repo.removeProductFromCart(userId, productId)
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
                repo.incrementProductNumberInCart(userId, productId)
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
                repo.decrementProductNumberInCart(userId, productId)
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
                _productNumber.value = repo.getNumberOfItemInCart(userId, productId)
            } catch (e: Exception) {
                Log.e("cart", "Error getting the product number")
            }
        }
    }
}