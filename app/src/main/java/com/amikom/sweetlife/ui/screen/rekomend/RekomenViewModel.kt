package com.amikom.sweetlife.ui.screen.rekomend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RekomenViewModel @Inject constructor(
    private val rekomendRepository: RekomenRepository
) : ViewModel() {
        private val _isLoading = MutableLiveData(false)
        val isLoading: LiveData<Boolean> = _isLoading

        private val _error = MutableLiveData<String?>()
        val error: LiveData<String?> = _error

        private val _rekomend = MutableLiveData<List<RekomendationData>>()
        val rekomend: LiveData<List<RekomendationData>> get() = _rekomend

    fun fetchRekomend() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)
            try {
                val entries = rekomendRepository.fetchRekomend() as List<RekomendationData>
                _rekomend.postValue(rekomend.value?.plus(entries) ?: entries)
            } catch (e: Exception) {
                _error.postValue("Failed to load rekomend: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}