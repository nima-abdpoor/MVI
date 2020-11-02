package com.chinachino.mvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chinachino.mvi.ui.main.state.MainStateEvent
import com.chinachino.mvi.ui.main.state.MainStateEvent.*
import com.chinachino.mvi.ui.main.state.MainViewState
import com.chinachino.mvi.model.BlogPost
import com.chinachino.mvi.model.User
import com.chinachino.mvi.repository.main.MainRepository
import com.chinachino.mvi.utils.AbsentLiveData
import com.chinachino.mvi.utils.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewSate: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewSate

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { _stateEvent ->
            _stateEvent?.let {
                handleStateEvent(_stateEvent)
            }

        }

    private fun handleStateEvent(_stateEvent: MainStateEvent?): LiveData<DataState<MainViewState>> {
        return when (_stateEvent) {
            is GetBlogPostsEvent -> {
                MainRepository.getBlogPosts()
            }
            is GetUserEvent -> {
                MainRepository.getUser(_stateEvent.userId)
            }
            is None -> {
                AbsentLiveData.create()
            }
            else ->{
                AbsentLiveData.create()
            }
        }
    }
    fun setBlogPostData(blogPosts: List<BlogPost>){
        var update = getCurrentViewState()
        update.blogPosts = blogPosts
        _viewSate.value =update
    }

    fun setUserData(user : User){
        var update = getCurrentViewState()
        update.user = user
        _viewSate.value = update
    }

    private fun getCurrentViewState() : MainViewState{
        return _viewSate.value?.let {
            it
        }?:MainViewState()
    }
    fun setStateEvent(state  :MainStateEvent){
        _stateEvent.value = state
    }
}