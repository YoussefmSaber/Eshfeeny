package com.example.eshfeenygraduationproject.onBoarding.viewPagerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eshfeenygraduationproject.databinding.SliderFragmentBinding

//  an adapter class to set the elements to viewpager automatically
class ViewPagerAdapter(private val onBoardingList: List<OnBoarding>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    //  the inner class for binding operation for the text and the image
    inner class ViewPagerHolder(private val binding: SliderFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(onBoarding: OnBoarding) {

            binding.txtPlaceHolder.setText(onBoarding.desc)
            binding.imgPlaceholder.setImageResource(onBoarding.photo)
        }
    }

    //  creates the viewpager holder so it can display the slider fragment in viewpager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {

        return ViewPagerHolder(
            SliderFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //  bind the items to it's position
    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.bindItem(onBoardingList[position])
    }

    //  returns the number of the items in the onboarding list
    override fun getItemCount(): Int {
        return onBoardingList.size
    }
}