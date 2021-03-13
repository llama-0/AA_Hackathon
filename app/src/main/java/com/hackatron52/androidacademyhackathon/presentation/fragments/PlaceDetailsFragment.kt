package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.presentation.fragment.BaseFragment
import com.hackatron52.androidacademyhackathon.presentation.command.PlaceDetailsCommand
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceDetailsScreenState
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.PlaceDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailsFragment :
    BaseFragment<PlaceDetailsScreenState, PlaceDetailsCommand, PlaceDetailsViewModel>(
        R.layout.fragment_place_detail,
        PlaceDetailsViewModel::class.java
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerPhoto: RecyclerView = view.findViewById(R.id.recyclerPhoto)
        val placeName = view.findViewById<TextView>(R.id.titlePlace)
        val addressPlace = view.findViewById<TextView>(R.id.addressPlace)
        val descriptionPlace = view.findViewById<TextView>(R.id.descriptionPlace)
        val recyclerReview = view.findViewById<RecyclerView>(R.id.recyclerReview)
        val fabFavorite = view.findViewWithTag<FloatingActionButton>(R.id.fabFavorite)
        val fabRoute = view.findViewById<FloatingActionButton>(R.id.fabRoute)

        fabFavorite.setOnClickListener {
            //TODO
        }

        fabRoute.setOnClickListener {
            //TODO
        }
    }

    override fun renderView(model: PlaceDetailsScreenState) {
        TODO("Not yet implemented")
    }

    override fun executeCommand(command: PlaceDetailsCommand) {
        when (command) {
            is PlaceDetailsCommand.OpenMapWithRoute -> showUnderdevelopmentMessage()
            is PlaceDetailsCommand.AddPlaceToFavorites -> showUnderdevelopmentMessage()
        }
    }

    companion object {
        private const val PLACE_KEY = "Place_item"

        fun newInstance(id: Int): PlaceDetailsFragment {
            val args = Bundle()
            args.putInt(PLACE_KEY, id)
            val fragment = PlaceDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}