package com.hackatron52.androidacademyhackathon.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.presentation.activity.NavBarShowingActivity
import com.hackatron52.androidacademyhackathon.presentation.navigation.Command
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.BaseViewModel

abstract class BaseFragment<
        ScreenState : Any,
        CommandType : Command,
        ViewModel : BaseViewModel<ScreenState, CommandType>>(
    @LayoutRes val layoutResId: Int,
    viewModelClass: Class<ViewModel>
) : Fragment(layoutResId) {

    protected lateinit var navController: NavController
        private set

    protected val viewModel: ViewModel by lazy { ViewModelProvider(this).get(viewModelClass) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBottomNavigationView()
        navController = Navigation.findNavController(view)
        subscribeToViewModelObservables()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
    }

    private fun subscribeToViewModelObservables() {
        val modelObserver = Observer<ScreenState>(this::renderView)
        viewModel.modelUpdate.observe(viewLifecycleOwner, modelObserver)
        val commandObserver = Observer<CommandType>(this::executeCommand)
        viewModel.commandsLiveData.observe(viewLifecycleOwner, commandObserver)
    }

    protected abstract fun renderView(model: ScreenState)

    protected open fun executeCommand(command: CommandType) {
        showUnderdevelopmentMessage()
    }

    protected fun showBottomNavigationView() {
        val activity = this.activity
        if (activity is NavBarShowingActivity) {
            activity.showBottomNavigationView()
        }
    }

    private fun hideBottomNavigationView() {
        val activity = this.activity
        if (activity is NavBarShowingActivity) {
            activity.hideBottomNavigationView()
        }
    }

    protected fun showUnderdevelopmentMessage() {
        view?.run {
            Snackbar.make(this, R.string.text_under_development, Snackbar.LENGTH_SHORT).show()
        }
    }
}