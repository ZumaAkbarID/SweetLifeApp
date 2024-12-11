package com.amikom.sweetlife.ui.screen.History

import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.HistoryResponse.HistoryResponse
import com.amikom.sweetlife.data.remote.dto.rekomen.RekomenResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

interface HistoryRepository {
    suspend fun fetchHistory(): Result<HistoryResponse>
}

