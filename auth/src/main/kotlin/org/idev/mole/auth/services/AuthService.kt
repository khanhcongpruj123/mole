package org.idev.mole.auth.services

import org.idev.mole.auth.provider.KeycloakProvider
import org.jboss.resteasy.client.jaxrs.ResteasyClient
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.token.TokenService
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Form

@Service
class AuthService(private val keycloakAdmin: Keycloak) {

    fun signUp(username: String, email: String, password: String): UserRepresentation {
        val userResource = keycloakAdmin.realm("mole").users()
        val client = keycloakAdmin.realm("mole").clients().findByClientId("mole").firstOrNull()
        val user = UserRepresentation().apply {
            this.username = username
            this.email = email

            val passwordCredential = CredentialRepresentation().apply {
                type = CredentialRepresentation.PASSWORD
                value = password
            }
            this.credentials = listOf(passwordCredential)

            this.isEnabled = true
            this.clientRoles = buildMap {
                this[client?.id] = listOf("user-role")
            }
        }
        val response = userResource.create(user)
        if (response.status >= 400) {
            throw Exception("Sign up error with status ${response.status}")
        }
        return user
    }

    fun signIn(username: String, password: String): AccessTokenResponse? {
        return KeycloakProvider.keycloakWithUsernameAndPassword(username, password).tokenManager().accessToken
    }

    fun refreshToken(refreshToken: String): AccessTokenResponse? {
        val client = ClientBuilder.newClient() as ResteasyClient
        val target: ResteasyWebTarget = client.target("http://localhost:8080")
        val tokenService = target.proxy(TokenService::class.java)
        val form = Form().apply {
            param(OAuth2Constants.GRANT_TYPE, OAuth2Constants.REFRESH_TOKEN)
            param(OAuth2Constants.REFRESH_TOKEN, refreshToken)
            param(OAuth2Constants.CLIENT_SECRET, "wRlc9SedbKfDr9xwWqnbzVOLJI7STYl1")
            param(OAuth2Constants.CLIENT_ID, "mole")
        }
        return tokenService.refreshToken("mole", form.asMap())
    }
}