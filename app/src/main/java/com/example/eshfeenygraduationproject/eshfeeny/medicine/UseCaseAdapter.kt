package com.example.eshfeenygraduationproject.eshfeeny.medicine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.CategoryResponseItem
import com.example.eshfeenygraduationproject.databinding.MedicineDetailsItemBinding

class UseCaseAdapter(): ListAdapter<CategoryResponseItem,UseCaseAdapter.UseCaseViewHolder>(UseCaseDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseCaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MedicineDetailsItemBinding.inflate(inflater, parent, false)
        return UseCaseViewHolder(binding)
    }
    override fun onBindViewHolder(holder: UseCaseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
    class UseCaseViewHolder(private val binding: MedicineDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryResponseItem) {
            binding.txtIdDetails.text = "${category.useCases}"
        }
    }
}


class UseCaseDiffCallback : DiffUtil.ItemCallback<CategoryResponseItem>() {
    override fun areItemsTheSame(
        oldItem: CategoryResponseItem,
        newItem: CategoryResponseItem
    ): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(
        oldItem: CategoryResponseItem,
        newItem: CategoryResponseItem
    ): Boolean {
        return oldItem == newItem
    }
}



