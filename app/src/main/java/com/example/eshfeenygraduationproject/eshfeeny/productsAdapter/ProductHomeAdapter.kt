package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.MedicineItemHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.home.HomeFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModel


class ProductHomeAdapter(
    private val viewModel: ProductViewModel,
    val userId: String,
    val favoritePorducts: ProductResponse
) :
    ListAdapter<ProductResponseItem, ProductHomeAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            MedicineItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i("CreateViewHolder sh8aal", itemBinding.toString())
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.i("onBindViewHolder sh8aal", toString())
    }

    inner class ViewHolder(private val itemBinding: MedicineItemHomeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(category: ProductResponseItem) {

            itemBinding.medicineNameIdTv.text = category.nameAr
            itemBinding.priceMedicineIdTv.text = "${category.price.toInt().toString()} جنيه  "
            // TODO: Change the Image to be the index [0] image[0]
            itemBinding.imgVMedicineId.loadUrl(category.images[0])

            itemBinding.add2CartBtn.setOnClickListener {
                itemBinding.add2CartBtn.visibility = View.GONE
                itemBinding.cardFunctionalityLayout.visibility = View.VISIBLE
            }


            itemBinding.imgVMedicineId.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(category._id)
                it.findNavController().navigate(action)
            }

            if (favoritePorducts.contains(category)) {
                itemBinding.heartIconId.setImageResource(R.drawable.favorite_fill)

                itemBinding.heartIconId.setOnClickListener {
                    viewModel.deleteFavoriteProduct(userId, category._id)
                    itemBinding.heartIconId.setImageResource(R.drawable.favorite_notfill)
                }
            } else {
                itemBinding.heartIconId.setImageResource(R.drawable.favorite_notfill)
                itemBinding.heartIconId.setOnClickListener {
                    viewModel.addMedicineToFavorites(userId, PatchProductId(category._id))
                    itemBinding.heartIconId.setImageResource(R.drawable.favorite_fill)
                }
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<ProductResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem
        ): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}