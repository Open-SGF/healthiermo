package org.healthiermo.homepage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class HomepageApplication {
    private static final Logger log = LoggerFactory.getLogger(HomepageApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HomepageApplication.class, args);
	}

    // Spring Boot functional interface that is used to execute logic right after the application fully initializes
    @Bean
    CommandLineRunner initAudioDirectory(
            @Value("${app.audio-files.path}") String audioFilesPath) {
        return args -> {
            Path dir = Path.of(audioFilesPath);
            if (Files.notExists(dir)) {
                try {
                    Files.createDirectories(dir);
                    log.info("Audio files directory created: {}", dir.toAbsolutePath());
                } catch (IOException e) {
                    log.error("Failed to create audio files directory: {}", dir.toAbsolutePath(), e);
                }
            } else {
                log.debug("Audio files directory already exists: {}", dir.toAbsolutePath());
            }
        };
    }
}
