package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.patchRequestVar.PatchString
import com.example.domain.entity.product.ProductResponseItem
import com.example.eshfeenygraduationproject.databinding.ProductItemCategoryBinding
import com.example.eshfeenygraduationproject.eshfeeny.favorite.FavoriteFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.varunest.sparkbutton.SparkEventListener


class ProductFavoriteAdapter(
    private val viewModel: ProductViewModel,
    val userId: String,
    val cartProducts: CartResponse
) :
    ListAdapter<ProductResponseItem, ProductFavoriteAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ProductItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: ProductItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var isFavorite = true
        private var productCount: Int = 0

        fun bind(product: ProductResponseItem) {

            setData2UI(product)
            setFavoriteIcon(product)
            addProduct2Cart(product)
            increaseProductAmount(product)
            decreaseProductAmount(product)
        }

        private fun setData2UI(product: ProductResponseItem) {
            itemBinding.medicineNameIdTv.text = product.nameAr
            itemBinding.priceMedicineIdTv.text = "${product.price.toInt().toString()} جنيه  "
            itemBinding.imgVMedicineId.loadUrl(product.images[0])
        }

        private fun getQuantityInCart(cartResponse: CartResponse, productId: String): Int {
            for (cartItem in cartResponse.cart) {
                if (cartItem.product._id == productId) {
                    Log.i("add 2 cart", "the product quantity ${cartItem.quantity}")
                    return cartItem.quantity
                }
            }
            return 0
        }

        private fun addProduct2Cart(product: ProductResponseItem) {
            itemBinding.add2CartBtn.setOnClickListener {
                productCount = getQuantityInCart(cartProducts, product._id)
                Log.i("Add 2 cart", productCount.toString())

                itemBinding.cardFunctionalityLayout.visibility = View.VISIBLE
                itemBinding.add2CartBtn.visibility = View.GONE

                if (productCount != 0) {
                    itemBinding.productAmount.text = productCount.toString()
                } else {
                    productCount++
                    viewModel.addProductToCart(userId, PatchString(product._id))
                    itemBinding.productAmount.text = productCount.toString()
                }
            }
        }

        private fun setFavoriteIcon(category: ProductResponseItem) {
            itemBinding.heartIconId.isChecked = true
            itemBinding.heartIconId.setEventListener(object : SparkEventListener {
                override fun onEvent(button: ImageView, buttonState: Boolean) {
                    if (buttonState) {
                        // Button is active
                        viewModel.addMedicineToFavorites(
                            userId,
                            PatchString(category._id)
                        )
                    } else {
                        // Button is inactive
                        viewModel.deleteFavoriteProduct(userId, category._id)
                    }
                }

                override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {

                }

                override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {

                }
            })
        }

        private fun increaseProductAmount(product: ProductResponseItem) {

            itemBinding.increaseBtnId.setOnClickListener {

                productCount++
                itemBinding.productAmount.text = productCount.toString()

                viewModel.incrementProductNumberInCart(userId, product._id)
            }
        }

        private fun decreaseProductAmount(product: ProductResponseItem) {

            itemBinding.decreaseBtnId.setOnClickListener {

                if (productCount == 1) {

                    productCount--

                    itemBinding.cardFunctionalityLayout.visibility = View.GONE
                    itemBinding.add2CartBtn.visibility = View.VISIBLE

                    viewModel.removeProductFromCart(userId, product._id)
                } else {

                    productCount--
                    viewModel.decrementProductNumberInCart(userId, product._id)

                    itemBinding.productAmount.text = productCount.toString()
                }
            }
        }
    }
}