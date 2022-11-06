package org.idev.mole.auth.services

import org.idev.mole.auth.models.Token
import org.idev.mole.auth.models.User

interface AuthService {
    /**
     * Create new user with username, email and password
     * New user will have user-role
     * */
    fun signUp(username: String, password: String, email: String) : User
    /**
     * Sign in with username and password
     * If success, return access token and refresh token
     * */
    fun signIn(username: String, password: String) : Token

    /**
     * Get access token by refresh token
     * */
    fun refreshToken(refreshToken: String) : Token
}