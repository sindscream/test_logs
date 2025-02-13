package com.eurotorg.test_logs.controller;

import com.eurotorg.test_logs.configuration.TestLogsProperties;
import com.eurotorg.test_logs.service.LogStreamingV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogController {

    private final LogStreamingV2Service logStreamingV2Service;

    private final TestLogsProperties testLogsProperties;

    @GetMapping("/v2/logs")
    public List<String> getLogs(
            @RequestParam String path, // Path to the log file
            @RequestParam(defaultValue = "0") int page, // Page number (default: 0)
            @RequestParam(defaultValue = "10") int size // Page size (default: 10)
    ) throws IOException {
        if (path.isEmpty()) {
            return logStreamingV2Service.getPaginatedLogs(testLogsProperties.getLogPath(), page, size);
        }
        return logStreamingV2Service.getPaginatedLogs(path, page, size);
    }

    @GetMapping("/v2/logs/d")
    public ResponseEntity<String> getLogs(
            ) throws IOException {
        return ResponseEntity.ofNullable("Test message");
    }
}
