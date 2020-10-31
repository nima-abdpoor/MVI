package com.chinachino.mvi.UI.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chinachino.mvi.UI.main.state.MainStateEvent
import com.chinachino.mvi.UI.main.state.MainStateEvent.*
import com.chinachino.mvi.UI.main.state.MainViewState
import com.chinachino.mvi.model.BlogPost
import com.chinachino.mvi.model.User
import com.chinachino.mvi.repository.main.MainRepository
import com.chinachino.mvi.utils.AbsentLiveData

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewSate: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewSate

    val dataState: LiveData<MainViewState> = Transformations
        .switchMap(_stateEvent) { _stateEvent ->
            _stateEvent?.let {
                handleStateEvent(_stateEvent)
            }

        }

    private fun handleStateEvent(_stateEvent: MainStateEvent?): LiveData<MainViewState> {
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

    fun getCurrentViewState() : MainViewState{
        var value  = _viewSate.value?.let {
            it
        }?:MainViewState()
        return value
    }
    fun setStateEvent(state  :MainStateEvent){
        _stateEvent.value = state
    }
}