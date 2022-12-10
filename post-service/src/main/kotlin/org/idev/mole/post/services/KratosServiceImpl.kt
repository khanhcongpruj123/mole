package org.idev.mole.post.services

import org.idev.mole.post.configs.OryConfigProperties
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class KratosServiceImpl(val oryConfigProperties: OryConfigProperties) : KratosService {

    override fun whoAmI(bearerToken: String) = WebClient.create(oryConfigProperties.kratos.url())
        .get()
        .uri("/sessions/whoami")
        .header("Authorization", "Bearer ${bearerToken}")
        .retrieve()
        .toEntity(Map::class.java)
        .block()?.body
}