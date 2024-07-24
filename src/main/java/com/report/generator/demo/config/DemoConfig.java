/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.report.generator.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@EnableAsync
@EnableJpaRepositories(basePackages = "com.report.generator.demo.repository")
@PropertySource({"classpath:application.properties"})
@ConditionalOnProperty(
    value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
public class DemoConfig {
    private static final int CORE_POOL_SIZE = 2;

    @Bean(name = "taskScheduler")
    public TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(CORE_POOL_SIZE);
        scheduler.initialize();
        return scheduler;
    }
}
