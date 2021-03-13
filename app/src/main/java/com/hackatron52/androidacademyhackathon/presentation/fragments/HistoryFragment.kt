package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.databinding.FragmentHistoryBinding
import com.hackatron52.androidacademyhackathon.presentation.adapter.HistoryAdapter
import com.hackatron52.androidacademyhackathon.presentation.command.HistoryCommand
import com.hackatron52.androidacademyhackathon.presentation.fragment.BaseFragment
import com.hackatron52.androidacademyhackathon.presentation.model.HistoryScreenState
import com.hackatron52.androidacademyhackathon.presentation.model.Place
import com.hackatron52.androidacademyhackathon.presentations.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment :
    BaseFragment<HistoryScreenState, HistoryCommand, HistoryViewModel>(
        R.layout.fragment_history,
        HistoryViewModel::class.java
    ) {

    private var adapter: HistoryAdapter? = null
    private var binding: FragmentHistoryBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
        setupAdapter()
        showBottomNavigationView()

        if (savedInstanceState == null) {
            viewModel.init()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        binding = null
    }

    private fun setupAdapter() {
        adapter = HistoryAdapter()
        binding?.rvHistoryList?.adapter = adapter
    }

    override fun renderView(model: HistoryScreenState) {
        updateList(model.historyList)
    }

    override fun executeCommand(command: HistoryCommand) {
        super.executeCommand(command)
    }

    fun updateList(list: List<Place>) {
        Log.d("TAG", "updateList: ${list.size} ")
        adapter?.submitList(list)
    }
}