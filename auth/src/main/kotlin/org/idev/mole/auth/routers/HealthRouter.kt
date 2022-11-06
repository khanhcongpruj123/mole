package org.idev.mole.auth.routers

import org.idev.mole.auth.dto.HealthResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthRouter {

    @GetMapping
    fun health() = ResponseEntity.ok().body(HealthResponse("OK"))
}