package com.amikom.sweetlife.ui.screen.AfterScan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.model.AfterScanModel
import com.amikom.sweetlife.domain.repository.AfterScanRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AfterScanViewModel @Inject constructor(
    private val repository: AfterScanRepository
) : ViewModel() {

    private val _afterScan = MutableLiveData<Result<AfterScanModel>>()
    val afterScan: LiveData<Result<AfterScanModel>> = _afterScan

    fun fetchDataAfterScan() {
        viewModelScope.launch {
            repository.fetchDataAfterScan().observeForever {
                _afterScan.postValue(it)
            }
        }
    }
}