package com.amikom.sweetlife.data.model

data class NewTokenModel (
    val accessToken: String,
    val refreshToken: String,
    val type: String
)