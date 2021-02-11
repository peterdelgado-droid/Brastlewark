package com.e.brastlewark.data.listener

interface APIListener<T> {
    fun onSuccess(model: T)
    fun onFailure(str: String)
}