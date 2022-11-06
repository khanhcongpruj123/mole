package org.idev.mole.auth.routers.v1

import org.idev.mole.auth.models.UserProfile
import org.idev.mole.auth.services.UserProfileService
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user/profile")
class UserProfileRouter(val userProfileService: UserProfileService) {

    @GetMapping
    fun getUserProfile(): ResponseEntity<UserProfile> {
        val auth = SecurityContextHolder.getContext().authentication as KeycloakAuthenticationToken
        val userProfile = userProfileService.getUserProfile(auth.principal.toString())
        return ResponseEntity.ok().body(userProfile)
    }
}