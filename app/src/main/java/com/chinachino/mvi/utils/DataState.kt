package com.chinachino.mvi.utils

data class DataState<T>(
    var message: String? = null,
    var data: T? = null,
    var loading: Boolean = false
) {
    companion object {
        fun <T> error(message: String): DataState<T> {
            return DataState(message = message , loading = false, data = null)
        }

        fun <T> data(message: String? , data : T?): DataState<T> {
            return DataState(message = message,loading = false,data = data)
        }

        fun <T> loading(isLoading : Boolean): DataState<T> {
            return DataState(message = null, loading = isLoading , data = null)
        }
    }

    override fun toString(): String {
        return "DataState(message=$message, data=$data, loading=$loading)"
    }

}