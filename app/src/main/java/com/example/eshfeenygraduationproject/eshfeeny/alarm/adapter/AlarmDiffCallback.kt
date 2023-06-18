package com.example.eshfeenygraduationproject.eshfeeny.alarm.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.alarm.Alarm
import com.example.domain.entity.product.ProductResponseItem

class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {
    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }

}