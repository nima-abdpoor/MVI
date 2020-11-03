package com.chinachino.mvi.api

import androidx.lifecycle.LiveData
import com.chinachino.mvi.model.BlogPost
import com.chinachino.mvi.model.User
import com.chinachino.mvi.utils.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") id: String)
            : LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts()
            : LiveData<GenericApiResponse<List<BlogPost>>>
}