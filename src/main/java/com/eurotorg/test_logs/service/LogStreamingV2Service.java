package com.eurotorg.test_logs.service;

import com.eurotorg.test_logs.configuration.TestLogsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogStreamingV2Service {

    private final TestLogsProperties testLogsProperties;

    public List<String> getPaginatedLogs(String logFilePath, int page, int size) throws IOException {
        // Read all lines from the log file
        List<String> allLines = Files.readAllLines(Paths.get(logFilePath));

        // Reverse the list to show the most recent logs first
        Collections.reverse(allLines);

        // Calculate pagination boundaries
        int start = page * size;
        int end = Math.min(start + size, allLines.size());

        if (start > allLines.size()) {
            return Collections.emptyList(); // Return empty list if page is out of bounds
        }

        return allLines.subList(start, end);
    }
}