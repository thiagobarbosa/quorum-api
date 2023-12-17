package com.quorum.api.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Paths
import org.springdoc.core.GroupedOpenApi
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@OpenAPIDefinition(
    info = Info(title = "Quorum API", version = "v1"),
    servers = [Server(url = "https://api.quorum-tech.io")]
)
class OpenApiConfiguration {
    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("User API")
            .pathsToMatch("/v1/**")
            .pathsToExclude("/v1/admin")
            .pathsToExclude("/v1/admin/**")
            .addOpenApiCustomiser(sortPathsCustomizer())
            .build()
    }

    @Bean
    fun adminApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("Admin API")
            .pathsToMatch("/v1/admin")
            .pathsToMatch("/v1/admin/**")
            .addOpenApiCustomiser(sortPathsCustomizer())
            .build()
    }

    fun sortPathsCustomizer(): OpenApiCustomiser {
        return OpenApiCustomiser { openApi ->
            val sortedPaths = Paths()
            openApi.paths.orEmpty().toSortedMap().forEach { (key, value) ->
                sortedPaths.addPathItem(key, value)
            }
            openApi.paths = sortedPaths
        }
    }
}
