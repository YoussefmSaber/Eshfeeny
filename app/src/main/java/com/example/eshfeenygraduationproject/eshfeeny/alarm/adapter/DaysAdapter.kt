package com.example.eshfeenygraduationproject.eshfeeny.alarm.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.DayItemViewBinding
import com.example.eshfeenygraduationproject.eshfeeny.Util.Days


class DaysAdapter(
    val daysList: List<Days>,
    private val onMonthNameChange: (String, String) -> Unit
) : RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION
    var currentMonthName = ""
    var currentYearNumber = ""

    inner class DaysViewHolder(val itemBinding: DayItemViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(days: Days) {
            itemBinding.dayNameTextView.text = days.dayName
            itemBinding.dayNumberTextView.text = days.dayNumber
            currentYearNumber = days.yearNumber

            if (currentMonthName != days.monthName) {
                currentMonthName = days.monthName
                onMonthNameChange(currentMonthName, currentYearNumber)
            }
            if (currentYearNumber != days.yearNumber) {
                currentYearNumber = days.yearNumber
            }

            settingAttributes()
        }
    }

    private fun DaysViewHolder.settingAttributes() {
        itemBinding.dayCardView.strokeColor =
            if (adapterPosition == selectedPosition) ContextCompat.getColor(
                itemView.context,
                R.color.light_blue
            )
            else Color.GRAY

        itemBinding.dayCardView.setCardBackgroundColor(
            if (adapterPosition == selectedPosition)
                ContextCompat.getColor(itemView.context, R.color.light_blue)
            else
                Color.WHITE
        )

        itemBinding.dayNumberTextView.setTextColor(
            if (adapterPosition == selectedPosition)
                ContextCompat.getColor(itemView.context, R.color.blue_main)
            else
                Color.BLACK
        )

        itemBinding.dayNameTextView.setTextColor(
            if (adapterPosition == selectedPosition)
                ContextCompat.getColor(itemView.context, R.color.blue_main)
            else
                Color.BLACK
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaysViewHolder =
        DaysViewHolder(
            DayItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = daysList.size

    override fun onBindViewHolder(
        holder: DaysViewHolder,
        position: Int
    ) {
        val day = daysList[position]
        holder.bindItem(day)
        holder.itemView.setOnClickListener {
            val oldSelection = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(oldSelection)
            notifyItemChanged(selectedPosition)
        }
    }
}
