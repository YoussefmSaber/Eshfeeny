package com.example.eshfeenygraduationproject.eshfeeny.publicViewModel

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.patchresponse.PatchRequestResponse
import com.example.domain.entity.product.ProductResponseItem
import kotlinx.coroutines.launch
import retrofit2.Response


class ProductViewModel(
    private val repo: ProductRepoImpl
) : ViewModel() {

    private val _remoteProducts = MutableLiveData<Response<ProductResponse>>()
    val remoteProducts: LiveData<Response<ProductResponse>>
        get() = _remoteProducts

    private val _remoteProductsAlt = MutableLiveData<Response<ProductResponse>>()
    val remoteProductsAlt: LiveData<Response<ProductResponse>>
        get() = _remoteProductsAlt

    private val _productDetails = MutableLiveData<ProductResponseItem>()
    val productDetails: LiveData<ProductResponseItem>
        get() = _productDetails


    //Categories For كل الادويه
    private val _allTypeProducts = MutableLiveData<ProductResponse>()
    val allTypeProducts: LiveData<ProductResponse>
        get() = _allTypeProducts

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

    fun getProductsFromRemote(medicine: String) {
        viewModelScope.launch {
            try {
                val response = repo.getProductsFromRemote(medicine)
                _remoteProducts.value = response
            } catch (e: Exception) {
                Log.e(TAG, "ERROR FETCHING URLS " + e)
            }
        }
    }

    fun getProductFromRemote(productId: String) {
        viewModelScope.launch {
            try {
                val response = repo.getProductFromRemote(productId)
                _productDetails.value = response.body()
            } catch (e: Exception) {
                Log.e("Fetching Product", "Error getting product details $e")
            }
        }
    }

    // this function made for the home fragment cause the data is shown twice in 2 different recycler views
    fun getProductsFromRemoteAlt(medicine: String) {
        viewModelScope.launch {
            try {
                val response = repo.getProductsFromRemote(medicine)
                _remoteProductsAlt.value = response
            } catch (e: Exception) {
                Log.e(TAG, "ERROR FETCHING URLS " + e)
            }
        }
    }

    fun getProductType(productType: String) {
        viewModelScope.launch {
            try {
                val response = repo.getProductType(productType)
                _allTypeProducts.value = response
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
                repo.addMedicineToFavorites(userId, productId)
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
                Log.i("details", _productNumber.value.toString())
            } catch (e: Exception) {
                Log.e("cart", "Error getting the product number")
            }
        }
    }
}