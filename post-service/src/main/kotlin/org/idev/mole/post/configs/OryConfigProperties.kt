package org.idev.mole.post.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "ory")
class OryConfigProperties {
    var kratos: KratosConfigProperties = KratosConfigProperties()
}

class KratosConfigProperties {
    var useSsl: Boolean = false
    lateinit var host: String
    lateinit var port: String

    fun url(): String {
        val schema = if (useSsl) "https" else "http"
        return "${schema}://${host}:${port}"
    }
}

