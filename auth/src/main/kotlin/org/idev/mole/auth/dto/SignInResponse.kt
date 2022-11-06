package org.idev.mole.auth.dto

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String
)
