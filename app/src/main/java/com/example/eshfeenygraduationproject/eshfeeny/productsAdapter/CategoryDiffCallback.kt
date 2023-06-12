package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.product.ProductResponseItem

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