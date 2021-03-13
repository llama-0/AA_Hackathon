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
        setHasOptionsMenu(true)

        binding = FragmentPlaceDetailBinding.bind(view)
        binding?.fabFavorite?.setOnClickListener { viewModel.onFavoriteClicked() }
        binding?.fabRoute?.setOnClickListener { viewModel.onRouteClicked() }
        binding?.btnAddReview?.setOnClickListener { navController.navigate(R.id.action_navigation_place_detail_to_feedbackFragment) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // Показать остальные поля по аналогии
    override fun renderView(model: PlaceDetailsScreenState) {
        if (model.isPlaceReadyToShow) {
            binding?.let {
                with(it) {
                    titlePlace.text = model.place?.name
                    addressPlace.text = model.place?.address
                    descriptionPlace.text = model.place?.description
                }
            }

        }
    }

    override fun executeCommand(command: PlaceDetailsCommand) {
        when (command) {
            is PlaceDetailsCommand.OpenMapWithRoute -> showUnderdevelopmentMessage()
        }
    }

}