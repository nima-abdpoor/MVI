package com.chinachino.mvi.api

import com.chinachino.mvi.model.BlogPost
import com.chinachino.mvi.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") id: String): User

    @GET("placeholder/blogs/")
    fun getBlogPosts(): List<BlogPost>
}