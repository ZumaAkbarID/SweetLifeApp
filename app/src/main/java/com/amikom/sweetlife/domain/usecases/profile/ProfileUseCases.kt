package com.amikom.sweetlife.domain.usecases.profile

data class ProfileUseCases(
    val fetchDataProfile: FetchDataProfile,
    val fetchDataHealthProfile: FetchDataHealthProfile,
    val updateDataProfile: UpdateDataProfile,
    val createHealthProfile: CreateHealthProfile
)