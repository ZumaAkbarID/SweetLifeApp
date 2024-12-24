    package com.amikom.sweetlife.ui.screen.profile.editProfile

    import android.content.Context
    import android.graphics.Bitmap
    import android.util.Log
    import androidx.datastore.core.IOException
    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.amikom.sweetlife.data.model.ProfileModel
    import com.amikom.sweetlife.data.remote.Result
    import com.amikom.sweetlife.data.remote.json_request.ProfileRequest
    import com.amikom.sweetlife.data.remote.retrofit.FeatureApiService
    import com.amikom.sweetlife.domain.repository.EditProfileRepository
    import com.amikom.sweetlife.domain.usecases.profile.ProfileUseCases
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch
    import okhttp3.MediaType
    import okhttp3.MediaType.Companion.toMediaTypeOrNull
    import okhttp3.MultipartBody
    import okhttp3.RequestBody
    import okhttp3.RequestBody.Companion.asRequestBody
    import java.io.ByteArrayOutputStream
    import java.io.File
    import java.io.FileOutputStream
    import javax.inject.Inject


    @HiltViewModel
    class EditProfileViewModel @Inject constructor(
        private val profileUseCases: ProfileUseCases,
        private val repository: EditProfileRepository,
        private val featureApiService: FeatureApiService
    ) : ViewModel() {

        private val modifiedFields = mutableSetOf<String>()

        //digunakana untuk menyimpan data profile yang diambil dari server
        private val _userProfile = MutableLiveData(ProfileModel())
        val userProfile: LiveData<ProfileModel> = _userProfile

        //digunakan untuk mengambil data profile dari server
        private val _profileState = MutableLiveData<Result<ProfileModel>>()
        val profileData: LiveData<Result<ProfileModel>> = _profileState

        private val _imageUploadStatus = MutableLiveData<String?>()
        val imageUploadStatus: LiveData<String?> = _imageUploadStatus

        private val _updateStatus = MutableLiveData<Boolean?>()
        val updateStatus: MutableLiveData<Boolean?> = _updateStatus

        private val _loading = MutableLiveData<Boolean>()
        val loading: LiveData<Boolean> = _loading

        private val _selectedGender = MutableStateFlow("")
        val selectedGender: StateFlow<String> = _selectedGender.asStateFlow()

        private val _profileLoadState = MutableStateFlow<LoadState>(LoadState.Initial)
        val profileLoadState: StateFlow<LoadState> = _profileLoadState.asStateFlow()

        private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
        val imageUploadState: StateFlow<ImageUploadState> = _imageUploadState.asStateFlow()

        init {
            fetchProfileData()
        }

        sealed class ImageUploadState {
            object Idle : ImageUploadState()
            object Loading : ImageUploadState()
            data class Success(val imageUrl: String?) : ImageUploadState()
            data class Error(val message: String) : ImageUploadState()
        }

        fun fetchProfileData() {
            viewModelScope.launch {
                _profileLoadState.value = LoadState.Loading
                try {
                    val result = profileUseCases.fetchDataProfile()
                    result.observeForever { profileResult ->
                        when (profileResult) {
                            is Result.Success -> {
                                _userProfile.value = profileResult.data
                                _profileLoadState.value = LoadState.Success
                            }

                            is Result.Error -> {
                                _profileLoadState.value = LoadState.Error("Error fetching profile data")
                            }

                            is Result.Loading -> {
                                _profileLoadState.value = LoadState.Loading
                            }

                            is Result.Empty -> {
                                _profileLoadState.value = LoadState.Error("Empty data")
                            }
                        }

                    }
                } catch (e: Exception) {
                    _profileLoadState.value = LoadState.Error(e.localizedMessage)
                }
            }
        }

        fun onNameChange(newName: String) {
            _userProfile.value = _userProfile.value?.copy(
                name = newName,
                email = _userProfile.value?.email ?: "",
                dateOfBirth = _userProfile.value?.dateOfBirth ?: "",
                gender = _userProfile.value?.gender ?: ""
            )
            modifiedFields.add("name")
        }


        fun onEmailChange(newEmail: String) {
            _userProfile.value =
                _userProfile.value?.copy(
                    email = newEmail,
                    name = _userProfile.value?.name ?: "",
                    dateOfBirth = _userProfile.value?.dateOfBirth ?: "",
                    gender = _userProfile.value?.gender ?: ""

                )
            modifiedFields.add("email")
        }

        fun onDateChange(newDate: String) {
            _userProfile.value = _userProfile.value?.copy(
                dateOfBirth = newDate,
                email = _userProfile.value?.email ?: "",
                name = _userProfile.value?.name ?: "",
                gender = _userProfile.value?.gender ?: ""
            )
            modifiedFields.add("dateOfBirth")
        }

        fun onGenderChange(newGender: String) {
            _userProfile.value = _userProfile.value?.copy(
                gender = newGender,
                email = _userProfile.value?.email ?: "",
                name = _userProfile.value?.name ?: "",
                dateOfBirth = _userProfile.value?.dateOfBirth ?: ""
            )
            modifiedFields.add("gender")
        }

        fun onImageChange(newImage: String) {
            _userProfile.value = _userProfile.value?.dateOfBirth?.let {
                _userProfile.value?.copy(
                    image = newImage,
                    email = _userProfile.value?.email ?: "",
                    name = _userProfile.value?.name ?: "",
                    dateOfBirth = _userProfile.value?.dateOfBirth ?: "",
                )
            }
            modifiedFields.add("image")
        }

        val GenderOptions = listOf("Female", "Male")

        fun updateSelectedType(type: String) {
            _selectedGender.value = type
        }


        // Modifikasi fungsi toMultipartBody untuk memastikan nama file sesuai
        fun Bitmap.toMultipartBody(
            context: Context,
            fileName: String = "profile_picture.jpg",
            quality: Int = 75
        ): MultipartBody.Part? {
            return try {
                // Validasi bitmap
                if (this.width == 0 || this.height == 0) {
                    Log.e("BitmapConversion", "Invalid bitmap dimensions")
                    return null
                }

                // Resize bitmap jika terlalu besar
                val resizedBitmap = resize(maxWidth = 1024, maxHeight = 1024)

                // Buat file sementara
                val file = File(context.cacheDir, fileName)
                FileOutputStream(file).use { out ->
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
                }

                // Validasi file
                if (!file.exists() || file.length() == 0L) {
                    Log.e("BitmapConversion", "File creation failed")
                    return null
                }

                // Buat request body
                val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

                // Buat MultipartBody.Part dengan nama field "profile_picture"
                MultipartBody.Part.createFormData("profile_picture", file.name, requestBody)

            } catch (e: Exception) {
                Log.e("BitmapConversion", "Error converting bitmap", e)
                null
            }
        }

        // Extension function untuk resize bitmap
        fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
            val width = this.width
            val height = this.height

            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

            var finalWidth = maxWidth
            var finalHeight = maxHeight

            if (ratioBitmap > ratioMax) {
                finalHeight = (maxWidth / ratioBitmap).toInt()
            } else {
                finalWidth = (maxHeight * ratioBitmap).toInt()
            }

            return Bitmap.createScaledBitmap(this, finalWidth, finalHeight, true)
        }

        fun prepareFilePart(file: File): MultipartBody.Part {
            val requestFile: RequestBody = RequestBody.create(
                "image/*".toMediaTypeOrNull(), // sesuaikan dengan tipe MIME file Anda
                file
            )
            return MultipartBody.Part.createFormData("profile_picture", file.name, requestFile)
        }
        fun updateProfilePicture(bitmap: Bitmap, context: Context) {
            viewModelScope.launch {
                try {
                    _imageUploadState.value = ImageUploadState.Loading

                    // Log detail bitmap
                    Log.d("BitmapDebug", "Bitmap width: ${bitmap.width}, height: ${bitmap.height}")
                    Log.d("BitmapDebug", "Bitmap size: ${bitmap.byteCount} bytes")

                    val imagePart = bitmap.toMultipartBody(context)

                    if (imagePart != null) {
                        // Log detail multipart
                        Log.d("MultipartDebug", "Multipart filename: ${imagePart.body.contentType()}")
                        Log.d("MultipartDebug", "Multipart size: ${imagePart.body.contentLength()} bytes")

                        val response = featureApiService.uploadProfileImage(imagePart)

                        // Log response details
                        Log.d("APIResponse", "Response code: ${response.code()}")
                        Log.d("APIResponse", "Response body: ${response.body()}")
                        Log.d("APIResponse", "Response message: ${response.message()}")

                        if (response.isSuccessful && response.body()?.status == true) {
                            _imageUploadState.value = ImageUploadState.Success("Image uploaded successfully")
                        } else {
                            _imageUploadState.value = ImageUploadState.Error("Failed to upload image: ${response.body()?.message}")
                        }
                    } else {
                        _imageUploadState.value = ImageUploadState.Error("Failed to convert image to multipart")
                    }
                } catch (e: Exception) {
                    Log.e("ImageUploadError", "Error details", e)
                    _imageUploadState.value = ImageUploadState.Error(e.localizedMessage ?: "Error occurred")
                }
            }
        }

        fun resetImageUploadState() {
            _imageUploadState.value = ImageUploadState.Idle
        }


        private val _isUpdating = MutableLiveData<Boolean>()
        val isUpdating: LiveData<Boolean> = _isUpdating

        fun setUpdating(isUpdating: Boolean) {
            _isUpdating.value = isUpdating
        }

        private fun logFieldUpdates() {
            modifiedFields.forEach {
                Log.d("EditProfileViewModel", "Modified field: $it")
            }
        }

        fun resetProfileData() {
            fetchProfileData()
            modifiedFields.clear()
        }

        fun resetStatus() {
            _updateStatus.value = null
            _imageUploadStatus.value = ImageUploadStatus.IDLE.toString()
        }


        fun updateProfile() {
            viewModelScope.launch {
                _loading.value = true
                try {
                    val currentProfile = _userProfile.value ?: return@launch

                    val updateFields = mutableMapOf<String, Any>()
                    if ("name" in modifiedFields) updateFields["name"] = currentProfile.name
                    if ("email" in modifiedFields) updateFields["email"] = currentProfile.email
                    if ("dateOfBirth" in modifiedFields) updateFields["dateOfBirth"] =
                        currentProfile.dateOfBirth
                    if ("gender" in modifiedFields) updateFields["gender"] = currentProfile.gender
                    if ("image" in modifiedFields) updateFields["image"] = currentProfile.image

                    if (updateFields.isNotEmpty()) {
                        val profileRequest = ProfileRequest(
                            name = updateFields["name"] as? String ?: currentProfile.name,
                            email = updateFields["email"] as? String ?: currentProfile.email,
                            dateOfBirth = updateFields["dateOfBirth"] as? String
                                ?: currentProfile.dateOfBirth,
                            gender = updateFields["gender"] as? String ?: currentProfile.gender,
                            image = updateFields["image"] as? String ?: currentProfile.image
                        )

                        val response = repository.updateProfile(profileRequest)
                        _updateStatus.value = response.status
                        if (response.status == true) modifiedFields.clear()
                    }
                } catch (e: Exception) {
                    Log.e("EditProfileViewModel", "updateProfile: ${e.message}")
                    _updateStatus.value = false
                } finally {
                    _loading.value = false
                }
            }
        }


        private fun isValidProfileData(): Boolean {
            val currentProfile = _userProfile.value ?: return false

            return currentProfile.name.isNotBlank() &&
                    currentProfile.email.isNotBlank() &&
                    android.util.Patterns.EMAIL_ADDRESS.matcher(currentProfile.email).matches() &&
                    currentProfile.dateOfBirth.isNotBlank() &&
                    GenderOptions.contains(currentProfile.gender)
        }


        sealed class LoadState {
            object Initial : LoadState()
            object Loading : LoadState()
            object Success : LoadState()
            data class Error(val message: String?) : LoadState()
        }

        enum class ImageUploadStatus {
            IDLE, UPLOADING, SUCCESS, ERROR
        }
    }
