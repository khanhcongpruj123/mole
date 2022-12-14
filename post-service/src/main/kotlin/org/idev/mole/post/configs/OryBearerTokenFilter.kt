package org.idev.mole.post.configs

import org.idev.mole.post.services.KratosService
import org.idev.mole.post.utils.bearerToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OryBearerTokenFilter(val kratosService: KratosService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken = request.bearerToken()
        if (!bearerToken.isNullOrEmpty()) {
            var sessionMap = kratosService.whoAmI(bearerToken)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken.authenticated(
                sessionMap,
                null,
                mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
            )
        }
        filterChain.doFilter(request, response)
    }

}