package org.idev.mole.post

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableJpaRepositories
class PostApp {}

fun main(args: Array<String>) {
    val context = runApplication<PostApp>(*args)
}