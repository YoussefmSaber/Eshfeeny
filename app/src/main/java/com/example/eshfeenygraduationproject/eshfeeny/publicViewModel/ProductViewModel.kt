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

    private val _productDetails = MutableLiveData<ProductResponseItem>()
    val productDetails: LiveData<ProductResponseItem>
        get() = _productDetails

    //Categories For Emsaak
    private val _categoriesEmsaak = MutableLiveData<ProductResponse>()
    val categoriesEmsaak: LiveData<ProductResponse>
        get() = _categoriesEmsaak

    //Categories For Ko7aa
    private val _categoriesKo7aa = MutableLiveData<ProductResponse>()
    val categoriesKo7aa: LiveData<ProductResponse>
        get() = _categoriesKo7aa

    //Categories For M8aas
    private val _categoriesM8aas = MutableLiveData<ProductResponse>()
    val categoriesM8aas: LiveData<ProductResponse>
        get() = _categoriesM8aas

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
                Log.i("Chip Click Test", response.toString())
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

    fun getMedicineForEmsaak() {
        viewModelScope.launch {
            try {
                val response = repo.getMedicineFromRemoteForEmsaak()
                _categoriesEmsaak.value = response
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
                _categoriesKo7aa.value = response
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
                _categoriesM8aas.value = response
                Log.i("mvvm sh8aal M8aas", toString())
            } catch (e: Exception) {
                // handle error
                Log.e(TAG, "Error fetching urls M8ass", e)
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun getProductType(productType: String) {
        viewModelScope.launch {
            try {
                val response = repo.getProductType(productType)
                _allTypeProducts.value = response
                Log.i("Chip Click Test", _allTypeProducts.value.toString())
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