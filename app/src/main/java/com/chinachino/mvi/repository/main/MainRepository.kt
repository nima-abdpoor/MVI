package com.chinachino.mvi.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.chinachino.mvi.UI.main.state.MainViewState
import com.chinachino.mvi.api.RetrofitBuilder
import com.chinachino.mvi.utils.ApiEmptyResponse
import com.chinachino.mvi.utils.ApiErrorResponse
import com.chinachino.mvi.utils.ApiSuccessResponse
import com.chinachino.mvi.utils.GenericApiResponse

object MainRepository{
    fun getBlogPosts() : LiveData<MainViewState>{
        return Transformations.switchMap(RetrofitBuilder.apiService.getBlogPosts()){response ->
            object : LiveData<MainViewState>() {
                override fun onActive() {
                    super.onActive()
                    when(response){

                        is ApiSuccessResponse ->{
                            value = MainViewState(blogPosts = response.body)
                        }
                        is ApiEmptyResponse ->{
                            value = MainViewState()
                        }
                        is ApiEmptyResponse ->{
                            value = MainViewState()
                        }
                    }
                }
            }
        }
    }

    fun getUser(userId : String) : LiveData<MainViewState>{
        return Transformations.switchMap(RetrofitBuilder.apiService.getUser(userId)){response ->
            object  : LiveData<MainViewState>(){
                override fun onActive() {
                    super.onActive()
                    when(response){
                        is ApiSuccessResponse ->{
                            value = MainViewState(user = response.body)
                        }
                        is ApiEmptyResponse ->{
                            value = MainViewState()
                        }
                        is ApiErrorResponse ->{
                            value = MainViewState()
                        }
                    }
                }
            }
        }
    }
}