package com.example.eshfeenygraduationproject.eshfeeny.medicine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.CategoryResponseItem
import com.example.eshfeenygraduationproject.databinding.MedicineDetailsItemBinding

class UsageAdapter(): ListAdapter<CategoryResponseItem, UsageAdapter.UsageViewHolder>(UsageDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MedicineDetailsItemBinding.inflate(inflater, parent, false)
        return UsageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
    class UsageViewHolder(private val binding: MedicineDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryResponseItem) {
            binding.txtIdDetails.text = "${category.usage}"
        }
    }
}
class UsageDiffCallback: DiffUtil.ItemCallback<CategoryResponseItem>() {
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