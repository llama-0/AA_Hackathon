package com.hackatron52.androidacademyhackathon.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.hackatron52.androidacademyhackathon.databinding.ShortPlacesInfoBinding
import com.hackatron52.androidacademyhackathon.presentation.model.Place
import com.hackatron52.androidacademyhackathon.presentation.viewholder.PlaceViewHolder

class PlaceAdapter(
    private val onPlaceClicked: (Place) -> Unit
) : ListAdapter<Place, PlaceViewHolder>(PlaceDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ShortPlacesInfoBinding
            .inflate(layoutInflater, parent, false)
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) =
        holder.bind(getItem(position), onPlaceClicked)

}