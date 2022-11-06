package org.idev.mole.auth.services

import org.idev.mole.auth.models.User
import org.idev.mole.auth.models.UserProfile

interface UserProfileService {

    /**
     * Get user profile by user id
     * */
    fun getUserProfile(userId: String) : UserProfile
}