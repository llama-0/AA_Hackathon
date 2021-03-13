package com.hackatron52.androidacademyhackathon.presentations.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hackatron52.androidacademyhackathon.presentations.model.HistoryDetail

class HistoryDiffUtil : DiffUtil.ItemCallback<HistoryDetail>() {
    override fun areItemsTheSame(oldItem: HistoryDetail, newItem: HistoryDetail): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: HistoryDetail, newItem: HistoryDetail): Boolean =
        oldItem.address == newItem.address &&
                oldItem.rating == newItem.rating &&
                oldItem.image == newItem.image
}