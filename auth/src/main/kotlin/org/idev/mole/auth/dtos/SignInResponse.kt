package org.idev.mole.auth.dtos

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String
)