package org.idev.mole.post.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import sh.ory.ApiClient
import sh.ory.api.IdentityApi

@Configuration
class KratosApiClientConfig {

    @Bean
    fun apiClientConfig(oryConfigProperties: OryConfigProperties): ApiClient = sh.ory.Configuration.getDefaultApiClient().apply {
        basePath = oryConfigProperties.kratos.url();
    }

    @Bean
    fun identityApiClient(kratosApiClient: ApiClient) = IdentityApi(kratosApiClient)

}