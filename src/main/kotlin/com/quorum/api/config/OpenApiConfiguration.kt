package com.quorum.api.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Paths
import org.springdoc.core.GroupedOpenApi
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(title = "Quorum API", version = "v1", description = "API de acesso aos dados de gastos da Câmara de Vereadores de São Paulo"),
    servers = [Server(url = "https://api.quorum-tech.io")]
)
class OpenApiConfiguration {
    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("User API")
            .pathsToMatch("/v1/**")
            .pathsToExclude("/v1/admin", "/v1/admin/**")
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
