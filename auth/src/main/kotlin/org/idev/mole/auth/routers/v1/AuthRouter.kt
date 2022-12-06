package org.idev.mole.auth.routers.v1

import org.idev.mole.auth.dtos.RefreshTokenRequest
import org.idev.mole.auth.dtos.SignInRequest
import org.idev.mole.auth.dtos.SignInResponse
import org.idev.mole.auth.dtos.SignUpRequest
import org.idev.mole.auth.mappers.SignInMapper
import org.idev.mole.auth.services.AuthServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthRouter(
    private val authService: AuthServiceImpl,
    private val signInMapper: SignInMapper
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> {
        val user = authService.signUp(username = request.username, password = request.password, email = request.email)
        return ResponseEntity<Void>(HttpStatus.CREATED)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: SignInRequest): ResponseEntity<SignInResponse> {
        val token = authService.signIn(username = request.username, password = request.password)
        return ResponseEntity.ok(signInMapper.map(token = token))
    }

    @PostMapping("/token/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<*> {
        return ResponseEntity.ok(authService.refreshToken(refreshToken = request.refreshToken))
    }
}