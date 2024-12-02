package com.amikom.sweetlife.domain.repository


import androidx.lifecycle.LiveData
import com.amikom.sweetlife.data.remote.Result as ApiResult
import com.amikom.sweetlife.data.remote.dto.rekomen.RekomenResponse
import javax.inject.Inject


interface RekomenRepository {
  suspend fun fetchRekomend(): ApiResult<RekomenResponse>
}
