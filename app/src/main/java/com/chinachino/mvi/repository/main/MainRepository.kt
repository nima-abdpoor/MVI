package com.chinachino.mvi.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.chinachino.mvi.UI.main.state.MainViewState
import com.chinachino.mvi.api.RetrofitBuilder
import com.chinachino.mvi.utils.ApiEmptyResponse
import com.chinachino.mvi.utils.ApiErrorResponse
import com.chinachino.mvi.utils.ApiSuccessResponse
import com.chinachino.mvi.utils.DataState

object MainRepository{
    fun getBlogPosts() : LiveData<DataState<MainViewState>>{
        return Transformations.switchMap(RetrofitBuilder.apiService.getBlogPosts()){response ->
            object : LiveData<DataState<MainViewState>>(){
                override fun onActive() {
                    super.onActive()
                    value = when(response){
                        is ApiSuccessResponse ->{
                            DataState.data(null,MainViewState(blogPosts = response.body))
                        }
                        is ApiEmptyResponse ->{
                            DataState.error("Data is Empty 204 response code!")
                        }
                        is ApiErrorResponse ->{
                            DataState.error(response.errorMessage)
                        }
                    }
                }
            }
        }
    }

    fun getUser(userId : String) : LiveData<DataState<MainViewState>>{
        return Transformations.switchMap(RetrofitBuilder.apiService.getUser(userId)){response ->
            object  : LiveData<DataState<MainViewState>>(){
                override fun onActive() {
                    super.onActive()
                    value = when(response){
                        is ApiSuccessResponse ->{
                            DataState.data(null,MainViewState(user = response.body))
                        }
                        is ApiEmptyResponse ->{
                            DataState.error("Data is Empty 204 response code!")
                        }
                        is ApiErrorResponse ->{
                            DataState.error(response.errorMessage)
                        }
                    }
                }
            }
        }
    }
}