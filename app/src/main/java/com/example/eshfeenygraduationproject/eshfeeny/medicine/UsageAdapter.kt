package com.example.eshfeenygraduationproject.eshfeeny.medicine


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eshfeenygraduationproject.databinding.MedicineDetailsItemBinding


class UsageAdapter(val usage: List<String>) :
    RecyclerView.Adapter<UsageAdapter.UsageViewHolder>() {
    inner class UsageViewHolder(private val binding: MedicineDetailsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(category: String) {
            binding.txtIdDetails.text = category
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsageViewHolder =
        UsageViewHolder(
            MedicineDetailsItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )

    override fun getItemCount(): Int = usage.size
    override fun onBindViewHolder(holder: UsageAdapter.UsageViewHolder, position: Int) {
        val day = usage[position]
        holder.bind(day)
    }
}





