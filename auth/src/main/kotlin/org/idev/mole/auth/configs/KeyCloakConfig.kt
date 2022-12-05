package org.idev.mole.auth.configs

import org.idev.mole.auth.dtos.KeycloakAdminProperties
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeyCloakConfig {

    @Bean("keycloakAdmin")
    fun keycloakAdmin(prop: KeycloakSpringBootProperties, adminProperties: KeycloakAdminProperties) = KeycloakBuilder.builder()
        .serverUrl(prop.authServerUrl)
        .realm(prop.realm)
        .clientId(prop.resource)
        .clientSecret(prop.credentials["secret"].toString())
        .username(adminProperties.adminUsername)
        .password(adminProperties.adminPassword)
        .resteasyClient(ResteasyClientBuilder().connectionPoolSize(20).build())
        .build()

    @Bean("moleRealmResource")
    fun moleRealmResource(keyCloakAdmin: Keycloak, prop: KeycloakSpringBootProperties) = keyCloakAdmin.realm(prop.realm)

    @Bean
    fun keycloakConfigResolver(): KeycloakConfigResolver? {
        return KeycloakSpringBootConfigResolver()
    }

}