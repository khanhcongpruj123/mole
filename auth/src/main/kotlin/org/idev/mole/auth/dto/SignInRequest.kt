package org.idev.mole.auth.dto

data class SignInRequest(
    val username: String,
    val password: String
)