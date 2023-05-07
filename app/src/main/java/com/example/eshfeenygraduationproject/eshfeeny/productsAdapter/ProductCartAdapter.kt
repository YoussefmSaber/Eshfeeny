package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
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
    private val lifecycleOwner: LifecycleOwner
): ListAdapter<CartItem, ProductCartAdapter.ViewHolder>(ProductCartDiffCallBack()) {

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
            var quantity = cartItem.quantity
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
                if(quantity>=1)
                quantity += 1
                else quantity = 1
                itemBinding.productAmount.text = quantity.toString()
                totalProductPrice = quantity * cartItem.product.price
                itemBinding.totalPriceTextView.text = "$totalProductPrice جنيه"
            }

            itemBinding.decrementAmountBtn.setOnClickListener {
                if (quantity != 1) {
                    viewModel.decrementProductNumberInCart(userId, cartItem.product._id)
                    quantity -= 1
                    itemBinding.productAmount.text = quantity.toString()
                    totalProductPrice = quantity * cartItem.product.price
                    itemBinding.totalPriceTextView.text = "$totalProductPrice جنيه"
                }
            }
            itemBinding.cartItemRemove.setOnClickListener {
                Log.i("cart", "Item removed")
                viewModel.removeProductFromCart(userId, cartItem.product._id)
                viewModel.getUserCartItems(userId)
                viewModel.cartItems.observe(lifecycleOwner) {
                    submitList(it.cart)
                }
                notifyItemChanged(adapterPosition, "remove")
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