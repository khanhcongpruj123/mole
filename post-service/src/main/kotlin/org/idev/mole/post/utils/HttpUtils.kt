package org.idev.mole.post.utils

import org.springframework.http.HttpHeaders
import javax.servlet.http.HttpServletRequest


fun HttpServletRequest.bearerToken(): String? = this.getHeader(HttpHeaders.AUTHORIZATION).let { authorizationStr ->
    if (authorizationStr.isNullOrEmpty()) {
        null
    } else {
        if (authorizationStr.startsWith("Bearer")) {
            authorizationStr.substring(7).trim().let { token -> token.ifBlank { null } }
        } else {
            null
        }
    }
}