package com.hackatron52.androidacademyhackathon.presentations.viewmodel

import com.hackatron52.androidacademyhackathon.presentation.command.HistoryCommand
import com.hackatron52.androidacademyhackathon.presentation.model.HistoryScreenState
import com.hackatron52.androidacademyhackathon.presentation.model.Place
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() :
    BaseViewModel<HistoryScreenState, HistoryCommand>(HistoryScreenState()) {

    private val testData = listOf(
        Place("1", "Хлеб и Вино ", "", " Ул Пушкина 1", 4.5, "", true),
        Place("2", "Кулинарная лавка", "", "проспект мира", 4.5, "", true),
        Place("3", "Coffix", "", "проспект мира", 4.5, "", true),
        Place("4", "Грузинская кухня", "", "ул Тверская", 4.5, "", true),
        Place("5", "Чайхона №1", "", "ул. Петровские Линии, 2/18", 4.5, "", true)
    )

    fun init() {
        updateScreenState(testData)
    }

    private fun updateScreenState(
        historyList: List<Place> = model.historyList,
        shouldRefreshView: Boolean = true
    ) {
        model = HistoryScreenState(historyList)
        if (shouldRefreshView) {
            refreshView()
        }
    }
}