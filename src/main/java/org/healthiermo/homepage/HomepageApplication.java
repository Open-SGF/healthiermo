package org.healthiermo.homepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@ComponentScan(basePackages = {"org.healthiermo.homepage"})
public class HomepageApplication {

	public static void main(String[] args) {

		Path currentRelativePath = Paths.get("");

		Path currentDir = currentRelativePath.toAbsolutePath();
		File file = new File(currentDir + "/src/main/resources/static/audio-files");
		//This is gonna have to change for prod.

		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		SpringApplication.run(HomepageApplication.class, args);
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		long size = 100000000000l;
		multipartResolver.setMaxUploadSize(size);
		return multipartResolver;
	}
}
