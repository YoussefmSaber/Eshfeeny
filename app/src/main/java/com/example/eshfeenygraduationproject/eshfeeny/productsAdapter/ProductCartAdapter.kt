package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.cart.CartItem
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.eshfeenygraduationproject.databinding.CartItemBinding
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel

class ProductCartAdapter(
    private val viewModel: ProductViewModel,
    private val userId: String,
) : ListAdapter<CartItem, ProductCartAdapter.ViewHolder>(ProductCartDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: CartItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var currentCartItem: CartItem? = null

        fun bind(cartItem: CartItem) {
            currentCartItem = cartItem
            var quantitiy = cartItem.quantity
            var totalProductPrice = cartItem.quantity * cartItem.product.price

            itemBinding.cartItemName.text =
                "${cartItem.product.nameAr} | ${cartItem.product.amount} | ${cartItem.product.volume}"

            Glide.with(itemBinding.root.context).load(cartItem.product.images[0])
                .into(itemBinding.cartItemImg)

            itemBinding.productAmount.text = "${cartItem.quantity}"

            itemBinding.totalPriceTextView.text = "$totalProductPrice جنيه"

            itemBinding.incrementAmountBtn.setOnClickListener {
                viewModel.incrementProductNumberInCart(userId, cartItem.product._id)
                //Edit functionality for counter in cart
                if(quantitiy>=1)
                quantitiy += 1
                else quantitiy = 1
                itemBinding.productAmount.text = quantitiy.toString()
                totalProductPrice = quantitiy * cartItem.product.price
                itemBinding.totalPriceTextView.text = "$totalProductPrice جنيه"
            }

            itemBinding.decrementAmountBtn.setOnClickListener {
                viewModel.decrementProductNumberInCart(userId, cartItem.product._id)
                //Edit functionality for counter in cart
                if (quantitiy>1)
                quantitiy -= 1
                else quantitiy = 1
                itemBinding.productAmount.text = quantitiy.toString()
                totalProductPrice = quantitiy * cartItem.product.price
                itemBinding.totalPriceTextView.text = "$totalProductPrice جنيه"
            }

            itemBinding.cartItemRemove.setOnClickListener {
                viewModel.removeProductFromCart(userId, PatchProductId(cartItem.product._id))
            }
        }
    }
}

class ProductCartDiffCallBack : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(
        oldItem: CartItem,
        newItem: CartItem
    ): Boolean {
        Log.i("cart", oldItem.product._id + " " + newItem.product._id)
        return oldItem.product._id == newItem.product._id
    }

    override fun areContentsTheSame(
        oldItem: CartItem,
        newItem: CartItem
    ): Boolean {
        Log.i("cart", oldItem.quantity.toString() + " " + newItem.quantity.toString())
        return oldItem.quantity == newItem.quantity
    }
}