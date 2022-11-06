package org.idev.mole.auth

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@EnableConfigurationProperties
class AuthApp { }

fun main(args: Array<String>) {
    val context = runApplication<AuthApp>(*args)
}