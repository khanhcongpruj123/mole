package org.idev.mole.post.configs

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggerConfig {

    @Bean
    fun logger() = KotlinLogging.logger { }
}