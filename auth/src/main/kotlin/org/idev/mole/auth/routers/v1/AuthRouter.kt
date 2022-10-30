package org.idev.mole.auth.routers.v1

import org.idev.mole.auth.services.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.Objects

@RestController
@RequestMapping("/api/v1/auth")
class AuthHandler(private val authService: AuthService) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequest) : ResponseEntity<Void> {
        val user = authService.signUp(username = request.username, password = request.password, email = request.email)
        return ResponseEntity<Void>(HttpStatus.CREATED)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: SignInRequest) : ResponseEntity<*> {
        return ResponseEntity.ok(authService.signIn(username = request.username, password = request.password))
    }

    @PostMapping("/token/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequest) : ResponseEntity<*> {
        return ResponseEntity.ok(authService.refreshToken(refreshToken = request.refreshToken))
    }
}

data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)

data class SignInRequest(
    val username: String,
    val password: String
)

data class RefreshTokenRequest(val refreshToken: String)