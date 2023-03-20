package com.example.eshfeenygraduationproject.eshfeeny.medicine

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.CategoryResponseItem
import com.example.eshfeenygraduationproject.databinding.MedicineItemsBinding


class MedicineAdapter() : ListAdapter<CategoryResponseItem, MedicineAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = MedicineItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
        Log.i("CreateViewHolder sh8aal",toString())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.i("onBindViewHolder sh8aal",toString())
    }

    class ViewHolder(private val itemBinding: MedicineItemsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: CategoryResponseItem) {
            itemBinding.medicineNameIdTv.text = category.nameAr
            // TODO: Change the Image to be the index [0] image[0]
            Glide.with(itemBinding.root.context).load(category.images).into(itemBinding.imgVMedicineId)
            Log.i("ViewHolder sh8aal",toString())
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryResponseItem>() {
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

}