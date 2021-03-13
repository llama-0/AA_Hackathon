package com.hackatron52.androidacademyhackathon.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.presentation.model.RefreshableScreenState
import com.hackatron52.androidacademyhackathon.presentation.navigation.Command
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.ScreenDataFetchingViewModel

abstract class RefreshableFragment<
        ScreenState : RefreshableScreenState<*>,
        CommandType : Command,
        ViewModel : ScreenDataFetchingViewModel<*, ScreenState, CommandType>>(
    @LayoutRes layoutResId: Int,
    viewModelClass: Class<ViewModel>
) : BaseFragment<ScreenState, CommandType, ViewModel>(layoutResId, viewModelClass) {

    private var layoutPullToRefresh: SwipeRefreshLayout? = null
    private var layoutErrorLoadingData: View? = null
    private var btnRetry: Button? = null
    private var tvErrorMessage: TextView? = null
    private var tvEmptyList: TextView? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRefreshableView(view)
    }

    private fun initRefreshableView(view: View) {
        layoutPullToRefresh = view.findViewById(R.id.layout_pull_to_refresh)
            ?: throw IllegalStateException("Wrong layout: no view with id = ${R.id.layout_pull_to_refresh}")
        layoutErrorLoadingData = view.findViewById(R.id.layout_error_loading_data)
            ?: throw IllegalStateException("Wrong layout: no view with id = ${R.id.layout_error_loading_data}")
        btnRetry = layoutErrorLoadingData?.findViewById(R.id.btnRetry)
        tvErrorMessage = layoutErrorLoadingData?.findViewById(R.id.tvErrorMessage)
        tvEmptyList = layoutErrorLoadingData?.findViewById(R.id.tv_empty_list)
        initListeners()
    }

    private fun initListeners() {
        btnRetry?.setOnClickListener {
            viewModel.onRetryButtonClicked()
        }
        layoutPullToRefresh?.setOnRefreshListener {
            viewModel.onPullToRefresh()
        }
    }

    @CallSuper
    override fun renderView(model: ScreenState) {
        updateLayoutPullToRefresh(model.isInLoadingState())
        updateErrorState(model.isInErrorState())
        updateEmptyListText(model.isEmptyListInContent())
    }

    private fun updateLayoutPullToRefresh(isLoading: Boolean) {
        layoutPullToRefresh?.isRefreshing = isLoading
    }

    private fun updateErrorState(isInErrorState: Boolean) {
        if (isInErrorState) {
            tvErrorMessage?.isVisible = true
            btnRetry?.isVisible = true
        } else {
            tvErrorMessage?.isVisible = false
            btnRetry?.isVisible = false
        }
    }

    private fun showErrorControlsIfRequired(isInErrorState: Boolean) {
        layoutErrorLoadingData?.isVisible = isInErrorState
    }

    private fun updateEmptyListText(isEmptyList: Boolean) {
        tvEmptyList?.isVisible = isEmptyList
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        layoutErrorLoadingData = null
        layoutPullToRefresh = null
        tvErrorMessage = null
        btnRetry = null
        tvEmptyList = null
    }
}