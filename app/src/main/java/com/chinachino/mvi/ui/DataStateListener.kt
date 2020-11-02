package com.chinachino.mvi.ui

import com.chinachino.mvi.utils.DataState

interface DataStateListener {

    fun onDataStateChanged(dataState : DataState<*>?)

}