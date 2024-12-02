package com.amikom.sweetlife.ui.screen.rekomend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amikom.sweetlife.data.remote.Result.Empty.onFailure
import com.amikom.sweetlife.data.remote.Result.Empty.onSuccess
import com.amikom.sweetlife.data.remote.dto.rekomen.FoodRecommendation
import com.amikom.sweetlife.domain.repository.RekomenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.amikom.sweetlife.data.remote.Result as ApiResult


@HiltViewModel
class RekomenViewModel @Inject constructor(
    private val rekomendRepository: RekomenRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _foodRecommendations = MutableLiveData<List<FoodRecommendation>>()
    val foodRecommendations: LiveData<List<FoodRecommendation>> = _foodRecommendations

    fun fetchRekomend() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)

            val result = rekomendRepository.fetchRekomend()
            result.onSuccess { response ->
                val foods = response.data?.foodRecommendation
                _foodRecommendations.postValue(foods ?: emptyList())
            }.onFailure { error ->
                _error.postValue(error)
            }

            _isLoading.postValue(false)
        }
    }
}
