package com.chinachino.mvi.ui.main.state

import com.chinachino.mvi.model.BlogPost
import com.chinachino.mvi.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null,
)