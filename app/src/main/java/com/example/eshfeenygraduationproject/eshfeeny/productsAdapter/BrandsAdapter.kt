package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Brands
import com.example.eshfeenygraduationproject.databinding.BrandsItemBinding
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl

class BrandsAdapter(
    private val brandsList: List<Brands>
) :
    RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {


    inner class ViewHolder(private val itemBinding: BrandsItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(brand: Brands) {
            itemBinding.brandImageView.loadUrl(brand.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        BrandsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = brandsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(brandsList[position])
    }
}