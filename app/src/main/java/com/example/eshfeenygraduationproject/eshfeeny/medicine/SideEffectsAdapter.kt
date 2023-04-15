package com.example.eshfeenygraduationproject.eshfeeny.medicine


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eshfeenygraduationproject.databinding.MedicineDetailsItemBinding


class SideEffectsAdapter(val sideEffect: List<String>) :
    RecyclerView.Adapter<SideEffectsAdapter.SideEffectViewHolder>() {
    inner class SideEffectViewHolder(private val binding: MedicineDetailsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(category: String) {
            binding.txtIdDetails.text = category
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SideEffectViewHolder =
        SideEffectViewHolder(
            MedicineDetailsItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )

    override fun getItemCount(): Int = sideEffect.size
    override fun onBindViewHolder(holder: SideEffectsAdapter.SideEffectViewHolder, position: Int) {
        val day = sideEffect[position]
        holder.bind(day)
    }
}





