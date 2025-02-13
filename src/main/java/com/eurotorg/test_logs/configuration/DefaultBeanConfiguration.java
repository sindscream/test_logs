package com.eurotorg.test_logs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultBeanConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "test-logs")
    public TestLogsProperties testLogsProperties()
    {
        return new TestLogsProperties();
    }
}
