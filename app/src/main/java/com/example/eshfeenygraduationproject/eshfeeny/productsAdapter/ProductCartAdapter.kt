package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eshfeenygraduationproject.databinding.MedicineDetailsItemBinding


class ProductCartAdapter(val warning: List<String>) :
    RecyclerView.Adapter<ProductCartAdapter.WarningViewHolder>() {
    inner class WarningViewHolder(private val binding: MedicineDetailsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(category: String) {
            binding.txtIdDetails.text = category
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WarningViewHolder =
        WarningViewHolder(
            MedicineDetailsItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )

    override fun getItemCount(): Int = warning.size
    override fun onBindViewHolder(holder: ProductCartAdapter.WarningViewHolder, position: Int) {
        val day = warning[position]
        holder.bind(day)
    }
}





