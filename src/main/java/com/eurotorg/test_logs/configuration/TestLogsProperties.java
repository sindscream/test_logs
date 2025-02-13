package com.eurotorg.test_logs.configuration;

import lombok.*;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Data
public class TestLogsProperties {

    String logPath;

    @PostConstruct
    public void log()
    {
        var logger = LoggerFactory.getLogger(this.getClass());
        logger.debug("GETERERR{}", this.logPath);
    }
}
