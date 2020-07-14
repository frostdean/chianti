package com.wmjun.chianti.infrastructure.async

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfiguration {

    @Bean
    fun asyncThreadTaskExecutor(): Executor =
            ThreadPoolTaskExecutor().let {
                it.corePoolSize = 8
                it.maxPoolSize = 8
                it
            }

}