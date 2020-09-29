package com.chinachino.mvi.UI.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chinachino.mvi.UI.main.state.MainViewState

class MainViewModel : ViewModel() {
    private val _viewSate: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewSate
}