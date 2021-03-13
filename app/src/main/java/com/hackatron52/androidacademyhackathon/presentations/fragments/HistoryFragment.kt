package com.hackatron52.androidacademyhackathon.presentations.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.databinding.FragmentHistoryBinding
import com.hackatron52.androidacademyhackathon.presentation.fragment.BaseFragment
import com.hackatron52.androidacademyhackathon.presentations.adapter.HistoryAdapter
import com.hackatron52.androidacademyhackathon.presentations.command.HistoryCommand
import com.hackatron52.androidacademyhackathon.presentations.model.HistoryDetail
import com.hackatron52.androidacademyhackathon.presentations.model.HistoryScreenState
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

        if (savedInstanceState != null) {
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

    fun updateList(list: List<HistoryDetail>) {
        Log.d("TAG", "updateList: ")
        adapter?.submitList(list)
    }
}