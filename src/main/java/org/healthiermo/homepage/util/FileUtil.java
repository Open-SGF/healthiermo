package org.healthiermo.homepage.util;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    @Value("${app.audio-files.path}")
    private Path uploadPath;

    @PostConstruct
    void init() throws IOException {
        Files.createDirectories(uploadPath);
        log.info("Upload directory initialized at: {}", uploadPath);
    }

    /**
     * Handles file upload for a given section.
     * Files are stored as: {page}-{section}.{extension}
     *
     * @param file    the uploaded file
     * @param page    the page identifier (e.g. "MISC", "OUTER")
     * @param section the section identifier (e.g. "OUTER_RING", "HELP")
     */
    public void handleFile(MultipartFile file, String page, String section) {
        if (file == null || file.isEmpty()) {
            log.debug("Skipping empty file for: {}-{}", page, section);
            return;
        }

        String originalFilename = file.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            log.warn("File has no name for: {}-{}", page, section);
            return;
        }

        String extension = getFileExtension(originalFilename);
        String sanitizedFilename = sanitizeFilename(page, section, extension);

        try {
            saveFile(file, sanitizedFilename);
            log.info("File saved successfully: {}", sanitizedFilename);
        } catch (IOException e) {
            log.error("Failed to save file: {} for {}-{}", sanitizedFilename, page, section, e);
        }
    }

    /**
     * Saves the file to the upload directory.
     */
    private void saveFile(MultipartFile file, String filename) throws IOException {
        log.info("Saving file in uploadPath: {}", uploadPath);
        Path targetLocation = uploadPath.resolve(filename).normalize();

        // Security: ensure the file is within the upload directory (prevent path traversal)
        if (!targetLocation.startsWith(uploadPath)) {
            throw new IOException("Cannot store file outside upload directory: " + filename);
        }

        try (OutputStream os = Files.newOutputStream(targetLocation)) {
            os.write(file.getBytes());
        }
    }

    /**
     * Extracts the file extension from a filename.
     * Returns empty string if no extension found.
     */
    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex + 1);
        }
        return "";
    }

    /**
     * Sanitizes the filename to prevent path traversal attacks.
     * Result: {page}-{section}.{extension}
     */
    private String sanitizeFilename(String page, String section, String extension) {
        // Remove any path separators or special characters
        String safePage = page.replaceAll("[^a-zA-Z0-9_-]", "");
        String safeSection = section.replaceAll("[^a-zA-Z0-9_-]", "");
        String safeExtension = extension.replaceAll("[^a-zA-Z0-9]", "");

        return safePage + "-" + safeSection + "." + safeExtension;
    }
}
