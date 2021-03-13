package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.os.Bundle
import android.view.View
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.databinding.FragmentPlaceDetailBinding
import com.hackatron52.androidacademyhackathon.presentation.command.PlaceDetailsCommand
import com.hackatron52.androidacademyhackathon.presentation.fragment.BaseFragment
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceDetailsScreenState
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.PlaceDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailsFragment :
    BaseFragment<PlaceDetailsScreenState, PlaceDetailsCommand, PlaceDetailsViewModel>(
        R.layout.fragment_place_detail,
        PlaceDetailsViewModel::class.java
    ) {

    private var binding: FragmentPlaceDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlaceDetailBinding.bind(view)

        binding?.fabFavorite?.setOnClickListener { viewModel::onFavoriteClicked }
        binding?.fabRoute?.setOnClickListener { viewModel.onRouteClicked() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // Показать остальные поля по аналогии
    override fun renderView(model: PlaceDetailsScreenState) {
        if (model.isPlaceReadyToShow) {
            binding?.titlePlace?.text = model.place?.name
        }
    }

    override fun executeCommand(command: PlaceDetailsCommand) {
        when (command) {
            is PlaceDetailsCommand.OpenMapWithRoute -> showUnderdevelopmentMessage()
            is PlaceDetailsCommand.AddPlaceToFavorites -> showUnderdevelopmentMessage()
        }
    }
}