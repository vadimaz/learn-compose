package com.blogspot.vadim.learncompose.model

sealed class LoadResult<out T> {
    data object Loading: LoadResult<Nothing>()
    data class Success<T>(val data: T): LoadResult<T>()
}