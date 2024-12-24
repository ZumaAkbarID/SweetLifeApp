package com.amikom.sweetlife.data.remote.repository

import android.util.Log
import com.amikom.sweetlife.data.remote.dto.EditProfilePictResponse.EditProfilePictResponse
import com.amikom.sweetlife.data.remote.dto.profile.ProfileResponse
import com.amikom.sweetlife.data.remote.json_request.ProfileRequest
import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
import com.amikom.sweetlife.domain.repository.EditProfileRepository
import com.amikom.sweetlife.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private val apiService: FeatureApiService
) : EditProfileRepository {

    override suspend fun uploadProfileImage(imagePart: MultipartBody.Part): EditProfilePictResponse {
        // Memanggil API dan menerima Response<EditProfilePictResponse>
        val response = apiService.uploadProfileImage(imagePart)

        // Cek apakah request berhasil
        if (response.isSuccessful) {
            // Jika berhasil, kembalikan data respons
            return response.body() ?: EditProfilePictResponse(status = false, message = "No data")
        } else {
            // Jika gagal, kembalikan respons dengan status gagal
            return EditProfilePictResponse(status = false, message = "Failed to upload image")
        }
    }

    override suspend fun updateProfile(profile: ProfileRequest): ProfileResponse {
        return apiService.updateProfile(profile).body() ?: ProfileResponse()
        try {
            val response = apiService.updateProfile(profile)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("API error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("API", "Error: ${e.localizedMessage}")
            throw e
        }
    }
}