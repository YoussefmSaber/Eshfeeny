package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.patchRequestVar.PatchString
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import com.example.eshfeenygraduationproject.databinding.ProductItemCategoryBinding
import com.example.eshfeenygraduationproject.eshfeeny.brands.BrandItemsFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.varunest.sparkbutton.SparkEventListener

class BrandItemsAdapter(
    private val viewModel: ProductViewModel,
    val userId: String?,
    val favoriteProducts: ProductResponse?,
    val cartProducts: CartResponse?,
    val state: String
) : ListAdapter<ProductResponseItem, BrandItemsAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ProductItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: ProductItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var itemCount: Int = 0

        fun bind(product: ProductResponseItem) {

            setData2UI(product)
            navigate2Details(product)
            if (state != "guest") {
                addItemToCart(product)
                setFavoriteItem(product)
                incrementProductAmount(product)
                decrementProductAmount(product)
            }
        }

        private fun setData2UI(product: ProductResponseItem) {
            itemBinding.medicineNameIdTv.text = product.nameAr
            itemBinding.priceMedicineIdTv.text = "${product.price.toInt().toString()} جنيه  "
            itemBinding.imgVMedicineId.loadUrl(product.images[0])
        }

        private fun navigate2Details(product: ProductResponseItem) {
            itemBinding.imgVMedicineId.setOnClickListener {
                val action =
                    BrandItemsFragmentDirections.actionBrandItemsFragmentToDetailsFragment(
                        product._id
                    )
                it.findNavController().navigate(action)
            }
        }

        private fun setFavoriteItem(category: ProductResponseItem) {
            if (favoriteProducts?.contains(category)!!) {
                itemBinding.heartIconId.isChecked = true
            }

            itemBinding.heartIconId.setEventListener(object : SparkEventListener {
                override fun onEvent(button: ImageView, buttonState: Boolean) {
                    if (buttonState) {
                        // Button is
                        if (userId != null) {
                            viewModel.addMedicineToFavorites(
                                userId,
                                PatchString(category._id)
                            )
                        }
                    } else {
                        // Button is inactive
                        if (userId != null) {
                            viewModel.deleteFavoriteProduct(userId, category._id)
                        }
                    }
                }

                override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {

                }

                override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {

                }
            })
        }

        private fun addItemToCart(product: ProductResponseItem) {

            itemBinding.add2CartBtn.setOnClickListener {

                itemCount = getQuantityInCart(cartProducts!!, product._id)

                itemBinding.add2CartBtn.visibility = View.GONE
                itemBinding.cardFunctionalityLayout.visibility = View.VISIBLE

                if (itemCount != 0) {
                    itemBinding.productAmount.text = itemCount.toString()
                } else {
                    itemCount++
                    if (userId != null) {
                        viewModel.addProductToCart(userId, PatchString(product._id))
                    }
                    itemBinding.productAmount.text = itemCount.toString()
                }
            }
        }

        private fun getQuantityInCart(cartResponse: CartResponse, productId: String): Int {
            for (cartItem in cartResponse.cart) {
                if (cartItem.product._id == productId) {
                    return cartItem.quantity
                }
            }
            return 0
        }

        private fun incrementProductAmount(product: ProductResponseItem) {
            itemBinding.increaseBtnId.setOnClickListener {

                itemCount++
                itemBinding.productAmount.text = itemCount.toString()
                if (userId != null) {
                    viewModel.incrementProductNumberInCart(userId, product._id)
                }
            }
        }

        private fun decrementProductAmount(product: ProductResponseItem) {
            itemBinding.decreaseBtnId.setOnClickListener {
                if (itemCount == 1) {

                    if (userId != null) {
                        viewModel.removeProductFromCart(userId, product._id)
                    }
                    itemCount--
                    itemBinding.add2CartBtn.visibility = View.VISIBLE
                    itemBinding.cardFunctionalityLayout.visibility = View.GONE
                } else {
                    if (userId != null) {
                        viewModel.decrementProductNumberInCart(userId, product._id)
                    }
                    itemCount--
                    itemBinding.productAmount.text = itemCount.toString()
                }
            }
        }
    }
}