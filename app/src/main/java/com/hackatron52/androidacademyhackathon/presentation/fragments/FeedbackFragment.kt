package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.hackatron52.androidacademyhackathon.App
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.data.network.PreferencesProvider
import com.hackatron52.androidacademyhackathon.databinding.FragmentFeedbackBinding
import com.hackatron52.androidacademyhackathon.presentation.fragment.BaseFragment
import com.hackatron52.androidacademyhackathon.presentation.command.FeedbackCommand
import com.hackatron52.androidacademyhackathon.presentation.model.FeedbackScreenState
import com.hackatron52.androidacademyhackathon.presentation.fragments.viewmodel.FeedbackViewModel
import com.hackatron52.androidacademyhackathon.presentation.model.Feedback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackFragment :
    BaseFragment<FeedbackScreenState, FeedbackCommand, FeedbackViewModel>(
        R.layout.fragment_feedback,
        FeedbackViewModel::class.java,
    ) {

    private lateinit var feedbackText: String
    private lateinit var preferencesProvider: PreferencesProvider
    private lateinit var app: App

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = requireContext().applicationContext as App
    }

    private lateinit var feedbackScreenState: FeedbackScreenState

    private var binding: FragmentFeedbackBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesProvider = PreferencesProvider(app)
        getTextFromFeedbackField()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedbackBinding.bind(view)
        showBottomNavigationView()

        if (savedInstanceState == null) {
            viewModel.init()
        }

        binding?.btnSend?.setOnClickListener {
            clickSaveButton()
        }
    }

    override fun renderView(model: FeedbackScreenState) {}

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun executeCommand(command: FeedbackCommand) {
        super.executeCommand(command)
        when (command) {
            is FeedbackCommand.SendFeedBack -> showUnderdevelopmentMessage()
        }
    }

    private fun getTextFromFeedbackField() {
        feedbackText = binding?.editTextFeedback?.text.toString()
    }

    private fun clickSaveButton() {
        getTextFromFeedbackField()
        initScreenState()
        addToList()
        saveListToSharedPref()
    }

    private fun initScreenState() {
        feedbackScreenState = FeedbackScreenState(
            feedbackText,
            app.model.getListFeedbackFromModel()
        )
    }

    private fun addToList() {
        val savedFeedback = Feedback(
            feedbackScreenState.enterFeedback
        )
        val newList = feedbackScreenState.listFeedback.toMutableList()
        newList.add(savedFeedback)
        updateModelFeedback(feedbackList = newList)
        app.model.addFeedback(savedFeedback)
    }

    private fun updateModelFeedback(
        enterFeedback: String = feedbackScreenState.enterFeedback,
        feedbackList: List<Feedback> = feedbackScreenState.listFeedback,
    ) {
        feedbackScreenState = FeedbackScreenState(
            enterFeedback,
            feedbackList
        )
    }

    private fun saveListToSharedPref() {
        val listToJson = Gson().toJson(feedbackScreenState.listFeedback)
        app.model.saveSharedPref(listToJson.toString())
    }
}