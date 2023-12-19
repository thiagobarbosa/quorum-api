package com.quorum.api.config

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class LiquibaseConfig {

    @Primary
    @Bean
    fun liquibaseProperties(): LiquibaseProperties {
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

        return LiquibaseProperties().apply {
            this.changeLog = "classpath:/db/changelog/db.changelog-master.yaml"
            this.url = dbUrl
            this.user = dbUsername
            this.password = dbPassword
        }
    }
}
