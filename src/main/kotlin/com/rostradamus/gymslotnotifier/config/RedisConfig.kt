package com.rostradamus.gymslotnotifier.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

/**
 * @author rostradamus
 * created on 2021-05-23
 */
@Configuration
class RedisConfig {
    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory = LettuceConnectionFactory("localhost", 6379)
}