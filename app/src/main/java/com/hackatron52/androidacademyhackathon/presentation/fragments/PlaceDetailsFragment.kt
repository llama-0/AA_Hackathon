package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialElevationScale
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enterTransition = MaterialElevationScale(true).apply {
            duration = 300
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding = FragmentPlaceDetailBinding.bind(view)
        binding?.fabFavorite?.setOnClickListener { viewModel.onFavoriteClicked() }
        binding?.fabRoute?.setOnClickListener { viewModel.onRouteClicked() }
        binding?.btnAddReview?.setOnClickListener { navController.navigate(R.id.action_navigation_place_detail_to_feedbackFragment) }
        viewModel.init("1")
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
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(model.place?.photo)
                        .into(ivPhoto)
                    ratingBar.rating = model.place?.rating?.toFloat() ?: 0f
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