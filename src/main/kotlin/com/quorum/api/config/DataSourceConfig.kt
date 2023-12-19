package com.quorum.api.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean
    fun getDataSource(): DataSource {
        val dbUrl = try {
            SecretManagerAccessor.getSecret("dbUrl")
        } catch (e: Exception) {
            "jdbc:mysql://localhost:33061/quorum?useSSL=false&characterEncoding=utf8&character_set_server=utf8mb4"
        }

        val dbUsername = try {
            SecretManagerAccessor.getSecret("dbUsername")
        } catch (e: Exception) {
            "test"
        }

        val dbPassword = try {
            SecretManagerAccessor.getSecret("dbPassword")
        } catch (e: Exception) {
            "test"
        }

        return DataSourceBuilder.create()
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .url(dbUrl)
            .username(dbUsername)
            .password(dbPassword)
            .build()
    }
}
