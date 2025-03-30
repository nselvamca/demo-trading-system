package com.demo.trading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "orderExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);   // min threads
        executor.setMaxPoolSize(50);    // max threads
        executor.setQueueCapacity(100); // queue size before rejecting
        executor.setThreadNamePrefix("OrderAsync-");
        executor.initialize();
        return executor;
    }
}
