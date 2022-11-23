package org.idev.mole.auth.services

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

    override fun signUp(username: String, email: String, password: String): User {
        val userResource = moleRealmResource.users()
        // init request create user to keycloak server
        val userRepresentation = UserRepresentation().apply {
            this.username = username
            this.email = email

            val passwordCredential = CredentialRepresentation().apply {
                type = CredentialRepresentation.PASSWORD
                value = password
            }
            this.credentials = listOf(passwordCredential)

            this.isEnabled = true
        }
        // send create user request
        val response = userResource.create(userRepresentation)

        // check create response
        if (response.status >= 400) {
            throw Exception("Sign up error with status ${response.status}")
        }

        val userId: String = CreatedResponseUtil.getCreatedId(response)
        userResource.get(userId).run {
            // add user role for created user
            val client = moleRealmResource.clients()
                .findByClientId("mole")[0]
            val userRole = moleRealmResource.clients()[client.id].roles()["user-role"].toRepresentation()
            roles().clientLevel(client.id).add(listOf(userRole))
        }
        return userRepresentation.let {
            User(it.username, it.email, UserProfile())
        }
    }

    override fun signIn(username: String, password: String): Token {
        // send request to keycloak server to receive token
        return keycloakProvider.keycloakWithUsernameAndPassword(username, password).tokenManager()
            .accessToken.let {
                Token(it.token, it.refreshToken)
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