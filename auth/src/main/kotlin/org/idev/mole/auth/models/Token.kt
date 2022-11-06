package org.idev.mole.auth.models

data class Token(
    val accessToken: String,
    val refreshToken: String
)
