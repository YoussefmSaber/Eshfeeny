package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.insuranceCard.InsuranceCardX
import com.example.eshfeenygraduationproject.databinding.InsuranceCardItemBinding


class InsuranceCardAdapter()
    : ListAdapter<InsuranceCardX, InsuranceCardAdapter.ViewHolder>(InsuranceCardDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            InsuranceCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ViewHolder(private val itemBinding: InsuranceCardItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var currentCartItem: InsuranceCardX? = null

        fun bind(insuranceCardItem: InsuranceCardX) {
            currentCartItem = insuranceCardItem
            itemBinding.nameICId.text = insuranceCardItem.nameOnCard
            itemBinding.numICId.text = insuranceCardItem.number
            Glide.with(itemBinding.root.context).load(insuranceCardItem.imageURL)
                .into(itemBinding.imgIDIC)

        }
    }
}

class InsuranceCardDiffCallBack : DiffUtil.ItemCallback<InsuranceCardX>() {
    override fun areItemsTheSame(oldItem: InsuranceCardX, newItem: InsuranceCardX): Boolean {
        Log.i("cart", oldItem.name + " " + newItem.name)
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: InsuranceCardX, newItem: InsuranceCardX): Boolean {
        Log.i("cart", oldItem.number + " " + newItem.number)
        return oldItem.number == newItem.number
    }
}