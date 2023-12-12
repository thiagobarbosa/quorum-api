package com.quorum.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenAuthenticationFilter: TokenFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain? {
        http.csrf().disable()
            .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers("/v**/admin/**").hasRole("ADMIN")
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        return http.build()
    }
}
