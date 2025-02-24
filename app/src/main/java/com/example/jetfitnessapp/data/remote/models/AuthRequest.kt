package com.example.jetfitnessapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(

    val email: String,
    val password: String

)