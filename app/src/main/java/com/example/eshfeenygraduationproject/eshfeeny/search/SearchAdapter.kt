package com.example.eshfeenygraduationproject.eshfeeny.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.product.ProductResponseItem
import com.example.eshfeenygraduationproject.databinding.SearchResultItemBinding
import com.example.eshfeenygraduationproject.eshfeeny.home.HomeFragmentDirections

class SearchAdapter(
    private val language: String
) :
    ListAdapter<ProductResponseItem, SearchAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            SearchResultItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: SearchResultItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(product: ProductResponseItem) {
            when (language) {
                "arabic" -> itemBinding.searchItemTextView.text = product.nameAr
                "english" -> itemBinding.searchItemTextView.text = product.nameEn
            }
            itemBinding.searchItemTextView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(product._id)
                it.findNavController().navigate(action)
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