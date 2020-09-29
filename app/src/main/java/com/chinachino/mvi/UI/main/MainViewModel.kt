package com.chinachino.mvi.UI.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chinachino.mvi.UI.main.state.MainStateEvent
import com.chinachino.mvi.UI.main.state.MainStateEvent.*
import com.chinachino.mvi.UI.main.state.MainViewState
import com.chinachino.mvi.utils.AbsentLiveData

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewSate: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewSate

    val dataState: LiveData<MainStateEvent> = Transformations
        .switchMap(_stateEvent) { _stateEvent ->
            _stateEvent?.let {
                handleStateEvent(_stateEvent)
            }

        }

    private fun handleStateEvent(_stateEvent: MainStateEvent?): LiveData<MainStateEvent> {
        return when (_stateEvent) {
            is GetBlogPostsEvent -> {
                AbsentLiveData.create()
            }
            is GetUserEvent -> {
                AbsentLiveData.create()
            }
            is None -> {
                AbsentLiveData.create()
            }
            else ->{
                AbsentLiveData.create()
            }
        }
    }
}