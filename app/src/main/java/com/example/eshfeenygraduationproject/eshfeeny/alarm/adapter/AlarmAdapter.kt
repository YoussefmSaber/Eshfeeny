package com.example.eshfeenygraduationproject.eshfeeny.alarm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.alarm.Alarm
import com.example.eshfeenygraduationproject.databinding.AlarmItemViewBinding

class AlarmAdapter : ListAdapter<Alarm, AlarmAdapter.ViewHolder>(AlarmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = AlarmItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: AlarmItemViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(alarm: Alarm) {
            // Bind alarm data to the view
            itemBinding.timeTextView.text = alarm.alarmTime[0].toString()
            itemBinding.MedicineNameTextView.text = alarm.name
            itemBinding.MedicineDescTextView.text = alarm.notes
        }
    }
}