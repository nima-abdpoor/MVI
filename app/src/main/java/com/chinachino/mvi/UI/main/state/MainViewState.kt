package com.chinachino.mvi.UI.main.state

import com.chinachino.mvi.model.BlogPost
import com.chinachino.mvi.model.User

data class MainViewState(
    val blogPosts: List<BlogPost>? = null,
    val user: User? = null,
)