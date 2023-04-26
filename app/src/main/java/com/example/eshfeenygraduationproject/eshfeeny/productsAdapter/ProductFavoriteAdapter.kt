package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponseItem
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.MedicineItemCategoryBinding
import com.example.eshfeenygraduationproject.eshfeeny.favorite.FavoriteFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModel


class ProductFavoriteAdapter(
    private val viewModel: ProductViewModel,
    val userId: String,
    val cartProducts: CartResponse
) :
    ListAdapter<ProductResponseItem, ProductFavoriteAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            MedicineItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: MedicineItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var isFavorite = true
        private var itemCount: Int? = 1

        fun bind(product: ProductResponseItem) {

            setData2UI(product)
            setFavoriteIcon(product)
            addProduct2Cart(product)
            navigate2Details(product)
            increaseProductAmount(product)
            decreaseProductAmount(product)
        }

        private fun setData2UI(product: ProductResponseItem) {
            itemBinding.medicineNameIdTv.text = product.nameAr
            itemBinding.priceMedicineIdTv.text = "${product.price.toInt().toString()} جنيه  "
            itemBinding.imgVMedicineId.loadUrl(product.images[0])
        }

        private fun getQuantityInCart(cartResponse: CartResponse, productId: String): Int? {
            for (cartItem in cartResponse.cart) {
                if (cartItem.product._id == productId) {
                    return cartItem.quantity
                }
            }
            return null
        }

        private fun addProduct2Cart(product: ProductResponseItem) {

            itemBinding.add2CartBtn.setOnClickListener {

                itemCount = getQuantityInCart(cartProducts, product._id)

                itemBinding.cardFunctionalityLayout.visibility = View.VISIBLE
                itemBinding.add2CartBtn.visibility = View.GONE

                if (itemCount != null) {
                    itemBinding.productAmount.text = itemCount.toString()
                } else {
                    viewModel.addProductToCart(userId, PatchProductId(product._id))
                    itemBinding.productAmount.text = "1"
                }
            }
        }

        private fun navigate2Details(product: ProductResponseItem) {

            itemBinding.imgVMedicineId.setOnClickListener {

                val action =
                    FavoriteFragmentDirections.actionFavoriteFragment2ToDetailsFragment(
                        product._id
                    )
                it.findNavController().navigate(action)
            }
        }

        private fun setFavoriteIcon(category: ProductResponseItem) {

            itemBinding.heartIconId.setImageResource(R.drawable.favorite_fill)
            itemBinding.heartIconId.setOnClickListener {

                if (isFavorite) {

                    viewModel.deleteFavoriteProduct(userId, category._id)
                    itemBinding.heartIconId.setImageResource(R.drawable.favorite_notfill)
                } else {

                    viewModel.addMedicineToFavorites(userId, PatchProductId(category._id))
                    itemBinding.heartIconId.setImageResource(R.drawable.favorite_fill)
                }
                isFavorite = !isFavorite
            }
        }

        private fun increaseProductAmount(product: ProductResponseItem) {

            itemBinding.increaseBtnId.setOnClickListener {

                itemCount = itemCount?.plus(1)
                itemBinding.productAmount.text = itemCount.toString()

                viewModel.incrementProductNumberInCart(userId, product._id)
            }
        }

        private fun decreaseProductAmount(product: ProductResponseItem) {

            itemBinding.decreaseBtnId.setOnClickListener {

                if (itemCount == 1) {

                    itemCount = itemCount?.minus(1)

                    itemBinding.cardFunctionalityLayout.visibility = View.GONE
                    itemBinding.add2CartBtn.visibility = View.VISIBLE

                    viewModel.removeProductFromCart(userId, PatchProductId(product._id))
                } else {

                    itemCount = itemCount?.minus(1)
                    viewModel.decrementProductNumberInCart(userId, product._id)

                    itemBinding.productAmount.text = itemCount.toString()
                }
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<ProductResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem
        ): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}