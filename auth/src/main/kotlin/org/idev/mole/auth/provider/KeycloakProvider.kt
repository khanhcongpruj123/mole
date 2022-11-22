package org.idev.mole.auth.provider

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.stereotype.Component

@Component
class KeycloakProvider(private val prop: KeycloakSpringBootProperties) {

    fun keycloakWithUsernameAndPassword(username: String, password: String) = KeycloakBuilder.builder()
        .serverUrl(prop.authServerUrl)
        .realm(prop.realm)
        .clientId(prop.resource)
        .clientSecret(prop.credentials["secret"].toString())
        .username(username)
        .password(password)
        .resteasyClient(ResteasyClientBuilder().connectionPoolSize(20).build())
        .build()
}