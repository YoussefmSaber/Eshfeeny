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
import com.example.domain.entity.cart.CartItem
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.product.ProductResponseItem
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.CartItemBinding
import com.example.eshfeenygraduationproject.databinding.MedicineDetailsItemBinding
import com.example.eshfeenygraduationproject.databinding.MedicineItemCategoryBinding
import com.example.eshfeenygraduationproject.eshfeeny.search_for_medicines.MedicineCategoryFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModel


class ProductCartAdapter(private val viewModel: ProductViewModel, val userId: String) :
    ListAdapter<CartItem, ProductCartAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i("CreateViewHolder sh8aal", itemBinding.toString())
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.i("onBindViewHolder sh8aal", toString())
    }

    inner class ViewHolder(private val itemBinding: CartItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(product: CartItem) {

            itemBinding.cartItemName.text =
                "${product.product.nameAr} | ${product.product.amount} | ${product.product.volume}"
            itemBinding.productAmount.text = "${product.quantity}"

            Glide.with(itemBinding.root.context).load(product.product.images[0])
                .into(itemBinding.cartItemImg)

            val productPrice = product.quantity * product.product.price

            itemBinding.totalPriceTextView.text = "$productPrice جنيه"

            itemBinding.incrementAmountBtn.setOnClickListener {
                viewModel.incrementProductNumberInCart(userId, product.product._id)
            }

            itemBinding.decrementAmountBtn.setOnClickListener {
                viewModel.decrementProductNumberInCart(userId, product.product._id)
            }

            itemBinding.cartItemRemove.setOnClickListener {
                viewModel.deleteFavoriteProduct(userId, product.product._id)
            }

            Log.i("ViewHolder sh8aal", toString())
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(
            oldItem: CartItem,
            newItem: CartItem
        ): Boolean {
            return oldItem.product._id == newItem.product._id
        }

        override fun areContentsTheSame(
            oldItem: CartItem,
            newItem: CartItem
        ): Boolean {
            return oldItem.product == newItem.product
        }
    }
}