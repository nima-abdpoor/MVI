package com.chinachino.mvi.repository.main

import androidx.lifecycle.LiveData
import com.chinachino.mvi.ui.main.state.MainViewState
import com.chinachino.mvi.api.RetrofitBuilder
import com.chinachino.mvi.model.BlogPost
import com.chinachino.mvi.model.User
import com.chinachino.mvi.utils.ApiSuccessResponse
import com.chinachino.mvi.utils.DataState
import com.chinachino.mvi.utils.GenericApiResponse

object MainRepository {
    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleAPISuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value =
                    DataState.data(data = MainViewState(blogPosts = response.body), message = null)
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }


    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleAPISuccessResponse(response: ApiSuccessResponse<User>) {
                result.value =
                    DataState.data(data = MainViewState(user = response.body), message = null)
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()
    }
}