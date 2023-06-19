package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.insuranceCard.InsuranceCardX
import com.example.eshfeenygraduationproject.databinding.InsuranceCardItemBinding

class InsuranceCardAdapter() : ListAdapter<InsuranceCardX, InsuranceCardAdapter.ViewHolder>(InsuranceCardDiffCallBack()) {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = InsuranceCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(private val itemBinding: InsuranceCardItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(insuranceCardItem: InsuranceCardX?, position: Int) {
            insuranceCardItem?.let { item ->
                itemBinding.nameICId.text = item.nameOnCard
                itemBinding.numICId.text = item.number
                Glide.with(itemBinding.root.context).load(item.imageURL).into(itemBinding.imgIDIC)

                if (selectedItemPosition == position) {
                    // Change background color and stroke for the selected item
                    itemBinding.MaterialCardIDIC.strokeColor = Color.parseColor("#F99D1C")
                    itemBinding.imageView10.visibility = View.VISIBLE
                    itemBinding.MaterialCardIDIC.setBackgroundColor(Color.parseColor("#FFE5CC"))
                } else {
                    // Reset background color and stroke for other items
                    itemBinding.MaterialCardIDIC.setBackgroundColor(Color.parseColor("#CCE6FF"))
                    itemBinding.MaterialCardIDIC.strokeColor = Color.parseColor("#E5E7EB")
                    itemBinding.imageView10.visibility = View.INVISIBLE
                }

                itemBinding.MaterialCardIDIC.setOnClickListener {
                    // Store the clicked position
                    val previousSelectedPosition = selectedItemPosition
                    selectedItemPosition = position

                    // Notify the adapter about the changes
                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedItemPosition)

                    Toast.makeText(itemBinding.root.context, "تم إرسال طلبك بنجاح", Toast.LENGTH_SHORT).show()
                }
            }
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