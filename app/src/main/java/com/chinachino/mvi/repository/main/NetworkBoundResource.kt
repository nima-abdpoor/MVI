package com.chinachino.mvi.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.chinachino.mvi.utils.*
import com.chinachino.mvi.utils.Constants.TESTING_TIME_LIMIT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject,ViewStateType>{
    protected var result =  MediatorLiveData<DataState<ViewStateType>>()
    init {
        result.value = DataState.loading(true)
        CoroutineScope(IO).launch {
            delay(TESTING_TIME_LIMIT)
            val apiResponse = createCall()
            withContext(Main){
               result.addSource(apiResponse){response ->
                   result.removeSource(apiResponse)
                   handleNetworkCall(response)

               }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when(response){
            is ApiSuccessResponse ->{
                handleAPISuccessResponse(response)
            }
            is ApiEmptyResponse ->{
                onReturnError("Data is Empty 204 response code!")
            }
            is ApiErrorResponse ->{
                onReturnError(response.errorMessage)
            }
        }

    }

    abstract fun handleAPISuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    private fun onReturnError(errorMessage: String) {
        result.value = DataState.error(message = errorMessage)
    }

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}