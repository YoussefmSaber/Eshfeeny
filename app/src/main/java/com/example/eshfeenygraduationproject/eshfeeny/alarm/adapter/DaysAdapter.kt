package com.example.eshfeenygraduationproject.eshfeeny.alarm.adapter

import android.graphics.Color
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.DayItemViewBinding
import com.example.eshfeenygraduationproject.eshfeeny.util.Days

// a class to set the days in the calender view
class DaysAdapter(
    private val daysList: List<Days>,
    private val onMonthNameChange: (String, String) -> Unit
) : RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {

    // taking the position of the selected view and giving it the default value of RecyclerView.NO_POSITION
    var selectedPosition = RecyclerView.NO_POSITION
    // creating 2 variables to contain the month name and the year number respectively
    var currentMonthName = ""
    var currentYearNumber = ""

    // an inner class that creates the views and assign it's values
    inner class DaysViewHolder(val itemBinding: DayItemViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        // a function that binds the values to the views
        fun bindItem(days: Days) {
            // giving the calender item it's values
            itemBinding.dayNameTextView.text = days.dayName
            itemBinding.dayNumberTextView.text = days.dayNumber
            // taking the current year number to check if it's changed or not
            currentYearNumber = days.yearNumber
            // if statements that set's the current month and year if it's values is changed
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

    // setting some attributes to the calender
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

    //  linking the items layout with the recycler view
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

    // getting the size of the list that has the days in it
    override fun getItemCount(): Int = daysList.size

    // binding view to the recycle view each with it value
    override fun onBindViewHolder(
        holder: DaysViewHolder,
        position: Int
    ) {
        val day = daysList[position]
        holder.bindItem(day)
        //  using this to change the color of the item when clicked
        holder.itemView.setOnClickListener {
            val oldSelection = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(oldSelection)
            notifyItemChanged(selectedPosition)
        }
    }
}
