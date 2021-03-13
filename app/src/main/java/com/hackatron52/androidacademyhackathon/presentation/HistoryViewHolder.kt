package com.hackatron52.androidacademyhackathon.presentations

import androidx.recyclerview.widget.RecyclerView
import com.hackatron52.androidacademyhackathon.databinding.ShortPlacesInfoBinding
import com.hackatron52.androidacademyhackathon.presentations.model.HistoryDetail

class HistoryViewHolder(
    val binding: ShortPlacesInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(historyDetail: HistoryDetail) {
        with(binding) {
            binding.tvPlaceAddress.text = historyDetail.address
            binding.tvPlaceTitle.text = historyDetail.name
            binding.tvPlaceRating.text = historyDetail.rating
            // TODO: 13.03.2021 load Image with Glide
        }
    }
}