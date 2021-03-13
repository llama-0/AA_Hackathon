package com.hackatron52.androidacademyhackathon.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.hackatron52.androidacademyhackathon.databinding.ShortPlacesInfoBinding
import com.hackatron52.androidacademyhackathon.presentation.model.Place
import com.hackatron52.androidacademyhackathon.presentation.viewholder.HistoryViewHolder

class HistoryAdapter : ListAdapter<Place, HistoryViewHolder>(HistoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ShortPlacesInfoBinding
            .inflate(layoutInflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(getItem(position))

}