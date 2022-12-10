package org.idev.mole.post.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class WebSecurityConfig(val oryBearerTokenFilter: OryBearerTokenFilter) {

    @Bean
    fun securityFilterChain(http: HttpSecurity) = http.httpBasic()
        .and()
        .csrf().disable()
        .cors()
        .and()
        .authorizeRequests()
        .antMatchers("/health/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(oryBearerTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()
}