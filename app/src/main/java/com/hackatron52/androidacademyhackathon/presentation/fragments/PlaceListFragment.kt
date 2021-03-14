package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.databinding.FragmentHistoryBinding
import com.hackatron52.androidacademyhackathon.presentation.adapter.PlaceAdapter
import com.hackatron52.androidacademyhackathon.presentation.command.PlaceListCommand
import com.hackatron52.androidacademyhackathon.presentation.fragment.BaseFragment
import com.hackatron52.androidacademyhackathon.presentation.model.Place
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceListScreenState
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.PlaceListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceListFragment :
    BaseFragment<PlaceListScreenState, PlaceListCommand, PlaceListViewModel>(
        R.layout.fragment_history,
        PlaceListViewModel::class.java
    ) {

    private var adapter: PlaceAdapter? = null
    private var binding: FragmentHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exitTransition = MaterialElevationScale(false).apply { duration = 300 }
        reenterTransition = MaterialElevationScale(false).apply { duration = 300 }
        enterTransition = MaterialFade().apply { duration = 300 }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        (requireActivity() as AppCompatActivity).supportActionBar
            ?.setDisplayHomeAsUpEnabled(false)
        view.doOnPreDraw { startPostponedEnterTransition() }
        binding = FragmentHistoryBinding.bind(view)
        setupAdapter()
        showBottomNavigationView()

        val arguments = PlaceListFragmentArgs.fromBundle(requireArguments())
        if (savedInstanceState == null) {
            viewModel.init(arguments.type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        binding = null
    }

    private fun setupAdapter() {
        adapter = PlaceAdapter(viewModel::onPlaceClicked)
        binding?.rvHistoryList?.adapter = adapter
    }

    override fun renderView(model: PlaceListScreenState) {
        updateList(model.historyList)
    }

    override fun executeCommand(command: PlaceListCommand) {
        when (command) {
            is PlaceListCommand.ShowPlaceDetail -> openPlaceDetail(command.id, command.view)
            else -> super.executeCommand(command)
        }
    }

    fun openPlaceDetail(placeId: String, view: View) {
        val args = PlaceDetailsFragmentArgs(placeId).toBundle()
        navController.navigate(R.id.navigation_place_detail, args)
    }

    private fun updateList(list: List<Place>) {
        Log.d("TAG", "updateList: ${list.size} ")
        adapter?.submitList(list)
    }
}