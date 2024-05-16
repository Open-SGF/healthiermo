package org.healthiermo.homepage.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static void write(MultipartFile file, String filename) {
        Path filepath = Paths.get("", "src/main/resources/static/audio-files/" + filename);
        System.out.println(filepath.toAbsolutePath());
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleFile(MultipartFile file, String page, String pie) {
        //Store files as page-pieChart.whatever
        if(file.isEmpty()) {
            System.out.println("Got empty file for combo: " + page +"-"+ pie);
            return;
        }

        String name = page + "-" + pie + "." +  FilenameUtils.getExtension(file.getOriginalFilename());
        System.out.println("Writing file: " + name);
        write(file, name);
    }
}