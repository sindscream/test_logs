package com.eurotorg.test_logs.service;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;

@Service
public class LogStreamingService {

    private final SimpMessagingTemplate messagingTemplate;

    public LogStreamingService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void init() {
        // Path to the Laravel log file (update this to your log file path)
        Path logFilePath = Paths.get("/Users/aleh/PhpstormProjects/emallphp/storage/logs/laravel.log");

        // Start a thread to monitor the log file
        new Thread(() -> {
            try {
                // Watch for changes in the log file
                WatchService watchService = FileSystems.getDefault().newWatchService();
                logFilePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                long filePointer = logFilePath.toFile().length(); // Track the file pointer

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.context().toString().equals(logFilePath.getFileName().toString())) {
                            // Read new lines from the log file
                            RandomAccessFile file = new RandomAccessFile(logFilePath.toFile(), "r");
                            file.seek(filePointer);

                            String line;
                            while ((line = file.readLine()) != null) {
                                // Send the new log line to WebSocket clients
                                messagingTemplate.convertAndSend("/topic/logs", line);
                            }

                            filePointer = file.getFilePointer();
                            file.close();
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}