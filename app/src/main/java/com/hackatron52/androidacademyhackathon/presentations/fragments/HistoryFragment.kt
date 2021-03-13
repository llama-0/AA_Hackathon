package com.hackatron52.androidacademyhackathon.presentations.fragments

import android.os.Bundle
import android.view.View
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.presentation.fragment.BaseFragment
import com.hackatron52.androidacademyhackathon.presentations.command.HistoryCommand
import com.hackatron52.androidacademyhackathon.presentations.model.HistoryScreenState
import com.hackatron52.androidacademyhackathon.presentations.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment :
    BaseFragment<HistoryScreenState, HistoryCommand, HistoryViewModel>(
        R.layout.fragment_history,
        HistoryViewModel::class.java
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavigationView()
    }

    override fun renderView(model: HistoryScreenState) {

    }

    override fun executeCommand(command: HistoryCommand) {
        super.executeCommand(command)
    }
}