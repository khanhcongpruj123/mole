package org.idev.mole.auth.services

import org.idev.mole.auth.models.Role
import org.idev.mole.auth.models.Token
import org.idev.mole.auth.models.User
import org.idev.mole.auth.models.UserProfile
import org.idev.mole.auth.provider.KeycloakProvider
import org.jboss.resteasy.client.jaxrs.ResteasyClient
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget
import org.keycloak.OAuth2Constants
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.token.TokenService
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Form

/**
 * @author idev
 * Authentication service
 * */

@Service
class AuthServiceImpl(
    @Qualifier("moleRealmResource")
    private val moleRealmResource: RealmResource,
    private val keycloakProvider: KeycloakProvider,
    private val keycloakProps: KeycloakSpringBootProperties
) : AuthService {

    override fun signUp(username: String, password: String, email: String): User {
        val userResource = moleRealmResource.users()
        // init request create user to keycloak server
        val userRepresentation = UserRepresentation().apply {
            // set username and email
            this.username = username
            this.email = email
            this.isEmailVerified = false

            // set password credential
            val passwordCredential = CredentialRepresentation().apply {
                type = CredentialRepresentation.PASSWORD
                value = password
            }
            this.credentials = listOf(passwordCredential)

            // default set it is enabled
            this.isEnabled = true
        }
        // send create user request
        val response = userResource.create(userRepresentation)

        // check create response
        if (response.status >= 400) {
            throw Exception("Sign up error with status ${response.status}")
        }

        val userId: String = CreatedResponseUtil.getCreatedId(response)

        // set role for new user
        userResource.get(userId).run {
            // add user role for created user
            val client = moleRealmResource.clients()
                .findByClientId(keycloakProps.resource)[0]
            val userRole = moleRealmResource.clients()[client.id].roles()[Role.USER.value].toRepresentation()
            roles().clientLevel(client.id).add(listOf(userRole))
        }

        // TODO send verify email

        return userRepresentation.let {
            User(it.username, it.email, UserProfile())
        }
    }

    override fun signIn(username: String, password: String): Token {
        // check user's email is verified
        val userResource = moleRealmResource.users()
        val users = userResource.search(username)
        if (users.isEmpty()) {
            throw RuntimeException("User not found")
        }
        if (users[0].isEmailVerified) {
            // send request to keycloak server to receive token
            return keycloakProvider.keycloakWithUsernameAndPassword(username, password).tokenManager()
                .accessToken.let {
                    Token(it.token, it.refreshToken)
                }
        } else {
            throw RuntimeException("User must verify email!")
        }
    }

    override fun refreshToken(refreshToken: String): Token {
        val client = ClientBuilder.newClient() as ResteasyClient
        val target: ResteasyWebTarget = client.target(keycloakProps.authServerUrl)
        val tokenService = target.proxy(TokenService::class.java)
        // init refresh form
        val form = Form().apply {
            param(OAuth2Constants.GRANT_TYPE, OAuth2Constants.REFRESH_TOKEN)
            param(OAuth2Constants.REFRESH_TOKEN, refreshToken)
            param(OAuth2Constants.CLIENT_SECRET, keycloakProps.credentials["secret"].toString())
            param(OAuth2Constants.CLIENT_ID, keycloakProps.resource)
        }
        // send request to keycloak server to get token
        return tokenService.refreshToken(keycloakProps.realm, form.asMap()).let {
            Token(it.token, it.refreshToken)
        }
    }
}