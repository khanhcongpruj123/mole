package org.idev.mole.auth.models

data class User(
    val username: String,
    val email: String,
    val userProfile: UserProfile
)
