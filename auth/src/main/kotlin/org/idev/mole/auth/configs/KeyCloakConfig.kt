package org.idev.mole.auth.configs

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeyCloakConfig {

    @Bean
    fun keycloakAdmin() = KeycloakBuilder.builder()
        .serverUrl("http://localhost:8080")
        .realm("mole")
        .clientId("mole")
        .clientSecret("wRlc9SedbKfDr9xwWqnbzVOLJI7STYl1")
        .username("idev")
        .password("123qweA!")
        .resteasyClient(ResteasyClientBuilder().connectionPoolSize(20).build())
        .build()

    @Bean
    fun keycloakConfigResolver(): KeycloakConfigResolver? {
        return KeycloakSpringBootConfigResolver()
    }

}