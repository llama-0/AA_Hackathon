package com.hackatron52.androidacademyhackathon.presentations.viewmodel

import com.hackatron52.androidacademyhackathon.presentation.viewmodel.BaseViewModel
import com.hackatron52.androidacademyhackathon.presentations.command.HistoryCommand
import com.hackatron52.androidacademyhackathon.presentations.model.HistoryDetail
import com.hackatron52.androidacademyhackathon.presentations.model.HistoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() :
    BaseViewModel<HistoryScreenState, HistoryCommand>(HistoryScreenState()) {

    private val testData = listOf(
        HistoryDetail("Хлеб и Вино", "Ул Пушкина 1", "7/10", ""),
        HistoryDetail("Кулинарная лавка", "проспект мира", "6/10", ""),
        HistoryDetail("Coffix", "проспект мира", "4/10", ""),
        HistoryDetail("Грузинская кухня", "ул Тверская", "8/10", ""),
        HistoryDetail("Чайхона №1", "ул. Петровские Линии, 2/18", "7/10", "")
    )

    fun init() {
        updateScreenState(testData)
    }

    private fun updateScreenState(
        historyList: List<HistoryDetail> = model.historyList,
        shouldRefreshView: Boolean = true
    ) {
        model = HistoryScreenState(historyList)
        if (shouldRefreshView) {
            refreshView()
        }
    }
}