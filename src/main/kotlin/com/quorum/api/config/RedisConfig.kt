package com.quorum.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val redisHost = try {
            SecretManagerAccessor.getSecret("redisHost")
        } catch (e: Exception) {
            "localhost"
        }

        val redisPort = try {
            SecretManagerAccessor.getSecret("redisPort").toInt()
        } catch (e: Exception) {
            6380
        }

        val redisStandaloneConfiguration = RedisStandaloneConfiguration(redisHost, redisPort)
        val jedisConnectionFactory = JedisConnectionFactory(redisStandaloneConfiguration)
        jedisConnectionFactory.afterPropertiesSet()
        return jedisConnectionFactory
    }

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        val redisHost = try {
            SecretManagerAccessor.getSecret("redisHost")
        } catch (e: Exception) {
            "localhost"
        }

        val redisStandaloneConfiguration = RedisStandaloneConfiguration(redisHost, 6379)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val connectionFactory = jedisConnectionFactory()
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(connectionFactory)
        template.setDefaultSerializer(StringRedisSerializer())
        template.afterPropertiesSet()
        return template
    }
}
