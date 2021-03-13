package com.hackatron52.androidacademyhackathon.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hackatron52.androidacademyhackathon.databinding.ShortPlacesInfoBinding
import com.hackatron52.androidacademyhackathon.presentation.model.Place

class PlaceViewHolder(
    val binding: ShortPlacesInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(historyDetail: Place) {
        with(binding) {
            tvPlaceAddress.text = historyDetail.address
            tvPlaceTitle.text = historyDetail.name
            tvPlaceRating.text = historyDetail.rating.toString()
            // TODO: 13.03.2021 load Image with Glide
        }
    }
}