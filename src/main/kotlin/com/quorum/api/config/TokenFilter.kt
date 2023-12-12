package com.quorum.api.config

import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class TokenFilter(
    private val authenticationService: AuthenticationService
) : GenericFilterBean() {

    companion object {
        private const val HEADER_TOKEN = "token"
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val accessToken = httpRequest.getHeader(HEADER_TOKEN)

        accessToken?.let {
            authenticationService.setSecurityContext(accessToken)
        }

        chain.doFilter(request, response)
    }
}
