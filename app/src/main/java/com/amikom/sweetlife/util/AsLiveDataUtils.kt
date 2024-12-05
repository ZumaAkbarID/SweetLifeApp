package com.amikom.sweetlife.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.StateFlow

fun <T> StateFlow<T>.asLiveData(): LiveData<T> = liveData {
    this@asLiveData.collect { emit(it) }
}