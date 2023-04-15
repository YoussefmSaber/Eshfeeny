package com.example.eshfeenygraduationproject.eshfeeny.medicine


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eshfeenygraduationproject.databinding.MedicineDetailsItemBinding


class UseCaseAdapter(val useCase: List<String>) :
    RecyclerView.Adapter<UseCaseAdapter.UseCaseViewHolder>() {
    inner class UseCaseViewHolder(private val binding: MedicineDetailsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(category: String) {
            binding.txtIdDetails.text = category
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UseCaseViewHolder =
        UseCaseViewHolder(
            MedicineDetailsItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )

    override fun getItemCount(): Int = useCase.size
    override fun onBindViewHolder(holder: UseCaseAdapter.UseCaseViewHolder, position: Int) {
        val day = useCase[position]
        holder.bind(day)
    }
}





