package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.databinding.FragmentHistoryBinding
import com.hackatron52.androidacademyhackathon.presentation.adapter.PlaceAdapter
import com.hackatron52.androidacademyhackathon.presentation.command.PlaceListCommand
import com.hackatron52.androidacademyhackathon.presentation.fragment.BaseFragment
import com.hackatron52.androidacademyhackathon.presentation.model.Place
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceListScreenState
import com.hackatron52.androidacademyhackathon.presentations.viewmodel.PlaceListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceListFragment :
    BaseFragment<PlaceListScreenState, PlaceListCommand, PlaceListViewModel>(
        R.layout.fragment_history,
        PlaceListViewModel::class.java
    ) {

    private var adapter: PlaceAdapter? = null
    private var binding: FragmentHistoryBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar
            ?.setDisplayHomeAsUpEnabled(false)

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
            is PlaceListCommand.ShowPlaceDetail -> openPlaceDetail(command.id)
            else -> super.executeCommand(command)
        }
    }

    fun openPlaceDetail(placeId: String) {
        val args = PlaceDetailsFragmentArgs(placeId).toBundle()
        navController.navigate(R.id.navigation_place_detail, args)
    }

    private fun updateList(list: List<Place>) {
        Log.d("TAG", "updateList: ${list.size} ")
        adapter?.submitList(list)
    }
}