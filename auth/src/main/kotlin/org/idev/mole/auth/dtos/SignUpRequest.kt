package org.idev.mole.auth.dtos

data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)