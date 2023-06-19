package com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.imageResponse.ImageResponse
import com.example.domain.entity.patchRequestVar.PatchString
import com.example.domain.entity.patchresponse.PatchRequestResponse
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File


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

    private val _deleteCartProduct = MutableLiveData<PatchRequestResponse>()
    val deleteCartProduct: LiveData<PatchRequestResponse>
        get() = _deleteCartProduct

    private val _cartItems: MutableLiveData<CartResponse> = MutableLiveData()
    val cartItems: LiveData<CartResponse>
        get() = _cartItems

    private val _productNumber: MutableLiveData<Int> = MutableLiveData()
    val productNumber: LiveData<Int>
        get() = _productNumber


    private val _alternativeProducts = MutableLiveData<ProductResponse>()
    val alternativeProducts: LiveData<ProductResponse>
        get() = _alternativeProducts

    private val _brandItems = MutableLiveData<ProductResponse>()
    val brandItems: LiveData<ProductResponse>
        get() = _brandItems

    private val _searchResults = MutableLiveData<ProductResponse>()
    val searchResults: LiveData<ProductResponse>
        get() = _searchResults

    private val _imageResponseResult = MutableLiveData<ImageResponse>()
    val imageResponseResult: LiveData<ImageResponse>
        get() = _imageResponseResult

    private val _imageSearchResults = MutableLiveData<ProductResponse>()
    val imageSearchResults: LiveData<ProductResponse>
        get() = _imageSearchResults

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
        productId: PatchString
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
                val favorite = repo.getFavoriteProducts(userId)
                if (favorite != null)
                    _favoriteProducts.value = favorite
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
                val cart = repo.getUserCartItems(userId)
                if (cart != null)
                    _cartItems.value = cart
            } catch (e: Exception) {
                Log.e("cart", "Error fetching the cart items")
            }
        }
    }

    fun addProductToCart(
        userId: String,
        productId: PatchString
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
        productId: String
    ) {
        viewModelScope.launch {
            try {
                val response = repo.removeProductFromCart(userId, productId)
                _deleteCartProduct.value = response
                Log.i("cart", _deleteCartProduct.toString())
            } catch (e: Exception) {
                Log.e("cart", "Error removing product from cart $e")
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
                val number = repo.getNumberOfItemInCart(userId, productId)
                if (number != null)
                    _productNumber.value = number
                Log.i("details", _productNumber.value.toString())
            } catch (e: Exception) {
                Log.e("cart", "Error getting the product number")
            }
        }
    }

    fun getAlternativeProducts(productId: String) {
        viewModelScope.launch {
            try {
                val alt = repo.getAlternativeProducts(productId)
                if (alt != null)
                    _alternativeProducts.value = alt
            } catch (e: Exception) {
                Log.i("Details Fragment", "Error fetching alternatives: $e")
            }
        }
    }

    fun getBrandItems(brandName: String) {
        viewModelScope.launch {
            try {
                val items = repo.getBrandItems(brandName)
                if (items != null)
                    _brandItems.value = items
            } catch (e: java.lang.Exception) {
                Log.i("Brand Fragment", "Error fetching Items: $e")
            }
        }
    }

    fun getSearchResults(productName: String) {
        viewModelScope.launch {
            try {
                val res = repo.getSearchResults(productName)
                if (res != null)
                    _searchResults.value = res
            } catch (e: Exception) {
                Log.i("Search Result", "Error fetching the results $e")
            }
        }
    }

    fun uploadImage(
        key: String,
        image: File
    ) {
        viewModelScope.launch {
            try {
                val res = repo.uploadImage(key, image)
                if (res != null)
                _imageResponseResult.value = res
                Log.i("image Response", "${_imageResponseResult.value}")
            } catch (e: Exception) {
                Log.i("Upload Image", "Error Sending The Image $e")
            }
        }
    }

    fun getImageUrl(
        imageUrl: String
    ) {
        viewModelScope.launch {
            try {
                val res = repo.getImageData(imageUrl)
                if (res != null)
                _imageSearchResults.value = res
            } catch (e: Exception) {
                Log.i("Upload Image", "Error Sending The Image $e")
            }
        }
    }
}