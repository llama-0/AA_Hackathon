package com.hackatron52.androidacademyhackathon.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hackatron52.androidacademyhackathon.presentation.model.Place

class HistoryDiffUtil : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean =
        oldItem.address == newItem.address &&
                oldItem.rating == newItem.rating &&
                oldItem.photo == newItem.photo
}