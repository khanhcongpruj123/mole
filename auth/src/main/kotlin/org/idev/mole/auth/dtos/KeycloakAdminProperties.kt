package org.idev.mole.auth.dtos

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "keycloak-admin")
class KeycloakAdminProperties {
    lateinit var adminUsername: String
    lateinit var adminPassword: String
}
