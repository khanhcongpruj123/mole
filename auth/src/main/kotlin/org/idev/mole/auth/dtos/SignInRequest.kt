package org.idev.mole.auth.dtos

data class SignInRequest(
    val username: String,
    val password: String
)