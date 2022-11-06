package org.idev.mole.auth.models

import java.time.LocalDateTime

data class UserProfile(
    val firstName: String? = null,
    val secondName: String? = null,
    val birthDay: LocalDateTime? = null
)
