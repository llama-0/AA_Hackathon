package com.hackatron52.androidacademyhackathon.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hackatron52.androidacademyhackathon.databinding.ShortPlacesInfoBinding
import com.hackatron52.androidacademyhackathon.presentation.model.Place

class PlaceViewHolder(
    val binding: ShortPlacesInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(place: Place, onPlaceClicked: (Place) -> Unit) {
        with(binding) {
            root.setOnClickListener { onPlaceClicked(place) }
            tvPlaceAddress.text = place.address
            tvPlaceTitle.text = place.name
            tvPlaceRating.text = place.rating.toString()
            // TODO: 13.03.2021 load Image with Glide
        }
    }
}