package com.hackatron52.androidacademyhackathon.presentation.model

import com.hackatron52.androidacademyhackathon.domain.Lce

interface RefreshableScreenWithActionsState<DataType, ActionResultDataType> :
    RefreshableScreenState<DataType> {
    val lceAction: Lce<ActionResultDataType>?

    fun isActionInProgress(): Boolean =
        lceAction?.loading == true

    fun isActionInError(): Boolean =
        lceAction?.error != null

    fun isActionCompletedSuccessfully(): Boolean =
        !isActionInProgress() && !isActionInError() && lceAction?.content != null
}