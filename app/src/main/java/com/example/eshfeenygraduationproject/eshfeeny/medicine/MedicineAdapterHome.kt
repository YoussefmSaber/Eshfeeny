package com.example.eshfeenygraduationproject.eshfeeny.medicine


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.CategoryResponse
import com.example.domain.entity.CategoryResponseItem
import com.example.domain.entity.patchRequestVar.AddToFavorites
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.MedicineItemHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.home.HomeFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModel


class MedicineAdapterHome(private val viewModel: MedicineViewModel, val userId: String, val favoritePorducts: CategoryResponse) :
    ListAdapter<CategoryResponseItem, MedicineAdapterHome.ViewHolder>(CategoryDiffCallback()) {

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

        fun bind(category: CategoryResponseItem) {

            itemBinding.medicineNameIdTv.text = category.nameAr
            itemBinding.priceMedicineIdTv.text = "${category.price.toInt().toString()} جنيه  "
            // TODO: Change the Image to be the index [0] image[0]
            Glide.with(itemBinding.root.context).load(category.images[0])
                .into(itemBinding.imgVMedicineId)
            var cnt = 1
            itemBinding.btnAddToCartId.setOnClickListener {
                itemBinding.btnAddToCartId.visibility = View.GONE
                itemBinding.increaseBtnId.visibility = View.VISIBLE
                itemBinding.decreaseBtnId.visibility = View.VISIBLE
                itemBinding.btnCntAddItemId.visibility = View.VISIBLE
                itemBinding.btnCntAddItemId.text = "1"
                cnt = 1
            }
            itemBinding.increaseBtnId.setOnClickListener {
                cnt++
                itemBinding.btnCntAddItemId.text = cnt.toString()
            }

            itemBinding.decreaseBtnId.setOnClickListener {
                cnt--
                if (cnt > 0)
                    itemBinding.btnCntAddItemId.text = cnt.toString()
                else {
                    itemBinding.btnCntAddItemId.text = "1"
                    cnt = 1
                }
            }

            itemBinding.btnCntAddItemId.setOnClickListener {
                itemBinding.btnAddToCartId.visibility = View.VISIBLE
                itemBinding.increaseBtnId.visibility = View.GONE
                itemBinding.decreaseBtnId.visibility = View.GONE
                itemBinding.btnCntAddItemId.visibility = View.GONE
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
                    viewModel.addMedicineToFavorites(userId, AddToFavorites(category._id))
                    itemBinding.heartIconId.setImageResource(R.drawable.favorite_fill)
                }
            }
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