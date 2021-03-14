package com.hackatron52.androidacademyhackathon.presentations.viewmodel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.hackatron52.androidacademyhackathon.presentation.PlaceListStatus
import com.hackatron52.androidacademyhackathon.presentation.command.PlaceListCommand
import com.hackatron52.androidacademyhackathon.presentation.model.Place
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceListScreenState
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceListViewModel @Inject constructor() :
    BaseViewModel<PlaceListScreenState, PlaceListCommand>(PlaceListScreenState()) {

    private val data = listOf(
        Place("1", "Хлеб и Вино ", "", " Ул Пушкина 1", 4.5, "", true),
        Place("2", "Кулинарная лавка", "", "проспект мира", 4.5, "", true),
        Place("3", "Coffix", "", "проспект мира", 4.5, "", true),
        Place("4", "Грузинская кухня", "", "ул Тверская", 4.5, "", true),
        Place("5", "Чайхона №1", "", "ул. Петровские Линии, 2/18", 4.5, "", true)
    )

    private val db = FirebaseDatabase.getInstance()
    private var snapshot: DataSnapshot? = null

    private fun getDataFromFirebase(node: String) {
        val ref = db.getReference(node)
        ref.get().continueWith { task ->
            if (task.isSuccessful) {
                snapshot = task.result
                Log.d("TAG", "getDataFromFirebase: $snapshot")
            } else {
                Log.e("TAG", "getDataFromFirebase: ", task.exception)
            }
        }
    }

    fun init(listType: Int) {
        val placeListStatus = PlaceListStatus.getStatusByOrdinalNumber(listType)
        if (placeListStatus == PlaceListStatus.Favorites) {
            getDataFromFirebase("Favorites")
            Log.d("TAG", "init: $data")
//            val data = snapshot?.map()
            updateScreenState(data)
        } else {
            getDataFromFirebase("History")
//            val data = snapshot?.map()
            updateScreenState(data)
        }
    }

    private fun updateScreenState(
        historyList: List<Place> = model.historyList,
        shouldRefreshView: Boolean = true
    ) {
        model = PlaceListScreenState(historyList)
        if (shouldRefreshView) {
            refreshView()
        }
    }

    fun onPlaceClicked(place: Place) {
        executeCommand(PlaceListCommand.ShowPlaceDetail(place.id))
    }
}