package com.darkndev.ktorauth.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token:String
)