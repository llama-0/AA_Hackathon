package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.hackatron52.androidacademyhackathon.domain.Lce
import com.hackatron52.androidacademyhackathon.presentation.model.RefreshableScreenState
import com.hackatron52.androidacademyhackathon.presentation.navigation.Command
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class ScreenDataFetchingViewModel<
        ScreenDataType,
        ScreenState : RefreshableScreenState<ScreenDataType>,
        SupportedCommandType : Command>
    (
    screenState: ScreenState
) : BaseRefreshableViewModel<ScreenState, SupportedCommandType>(screenState) {

    open fun init(id: String? = null) {
        fetchScreenData()
    }

    protected open fun fetchScreenData() {
        viewModelScope.launch {
            refreshViewForLce(Lce.loading())
            getFetchScreenData()
                ?.collect {
                    refreshViewForLce(it)
                }
        }
    }

    private fun refreshViewForLce(lce: Lce<ScreenDataType>?) {
        model = getUpdatedModelForLce(lce)
        refreshView()
    }

    protected abstract suspend fun getFetchScreenData(): Flow<Lce<ScreenDataType>>?

    protected abstract fun getUpdatedModelForLce(lce: Lce<ScreenDataType>?): ScreenState

    protected fun <ActionResultType> executeAction(
        actionRequest: suspend () -> Flow<Lce<ActionResultType>>,
        lceUpdateCallback: (Lce<ActionResultType>) -> Unit
    ) {
        lceUpdateCallback(Lce.loading())
        viewModelScope.launch {
            actionRequest()
                .collect {
                    lceUpdateCallback(it)
                }
        }
    }

    override fun reloadScreenData() =
        fetchScreenData()

    open fun onPullToRefresh() =
        reloadScreenData()

    open fun onRetryButtonClicked() =
        reloadScreenData()
}