package com.quorum.api.config

import com.github.benmanes.caffeine.cache.LoadingCache
import com.quorum.api.authentication.models.getRequestLimit
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenFilter(
    private val authenticationService: AuthenticationService,
    private val requestLimitCache: LoadingCache<String, Int>
) : GenericFilterBean() {

    companion object {
        private const val MAX_REQUESTS_PER_MINUTE = 5
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val headerToken = httpRequest.getHeader("token")

        if (headerToken != null) {
            val auth = try {
                authenticationService.getAuthenticationByToken(headerToken)
            } catch (e: Exception) {
                httpResponse.sendResponse(HttpServletResponse.SC_UNAUTHORIZED, e.message)
                return
            }

            val requests = requestLimitCache.get(auth.token) ?: 0

            if (requests >= auth.getRequestLimit()) {
                httpResponse.sendResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests. Please wait a minute a try again.")
                return
            }

            requestLimitCache.put(headerToken, requests + 1)
        }

        chain.doFilter(request, response)
    }

    fun HttpServletResponse.sendResponse(responseStatus: Int, message: String?) {
        setHeader("Access-Control-Allow-Origin", "*")
        setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS")
        setHeader("Access-Control-Allow-Headers", "*")
        setHeader("Access-Control-Max-Age", "180")
        status = responseStatus
        contentType = "application/json"
        characterEncoding = "UTF-8"
        outputStream.writer().use { it.write("{\"status\": \"${responseStatus}\", \"message\": \"${message}\"}") }
    }
    fun getClientIP(request: HttpServletRequest): String {
        val xfHeader = request.getHeader("X-Forwarded-For") ?: return request.remoteAddr
        return xfHeader.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0]
    }
}
