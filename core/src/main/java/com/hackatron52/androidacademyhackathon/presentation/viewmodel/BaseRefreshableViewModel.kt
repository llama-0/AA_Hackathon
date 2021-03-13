package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import androidx.annotation.CallSuper
import com.hackatron52.androidacademyhackathon.presentation.model.RefreshableScreen
import com.hackatron52.androidacademyhackathon.presentation.model.RefreshableScreenState
import com.hackatron52.androidacademyhackathon.presentation.navigation.Command

abstract class BaseRefreshableViewModel<
        ScreenState : RefreshableScreenState<*>,
        SupportedCommandType : Command>
    (
    initialState: ScreenState
) : BaseViewModel<ScreenState, SupportedCommandType>(initialState), RefreshableScreen {

    @CallSuper
    override fun onViewResumed() {
        super.onViewResumed()
        if (shouldLoadScreenDataOnScreenReopened()) {
            reloadScreenDataOnScreenReopened()
        }
    }

    protected open fun reloadScreenDataOnScreenReopened() =
        reloadScreenData()

    protected open fun shouldLoadScreenDataOnScreenReopened(): Boolean =
        (model.lce?.content == null || model.lce?.error != null) && model.lce?.loading != true
}