package org.idev.mole.auth.provider

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder

object KeycloakProvider {

    fun keycloakWithUsernameAndPassword(username: String, password: String) = KeycloakBuilder.builder()
        .serverUrl("http://localhost:8080")
        .realm("mole")
        .clientId("mole")
        .clientSecret("wRlc9SedbKfDr9xwWqnbzVOLJI7STYl1")
        .username(username)
        .password(password)
        .resteasyClient(ResteasyClientBuilder().connectionPoolSize(20).build())
        .build()

    fun keycloakWithAccessToken(username: String, password: String) = KeycloakBuilder.builder()
        .serverUrl("http://localhost:8080")
        .realm("mole")
        .clientId("mole")
        .clientSecret("wRlc9SedbKfDr9xwWqnbzVOLJI7STYl1")
        .grantType(OAuth2Constants.REFRESH_TOKEN)
        .resteasyClient(ResteasyClientBuilder().connectionPoolSize(20).build())
        .build()


}