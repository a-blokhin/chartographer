package com.ablokhin.chartographer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableCaching
public class ChartographerApplication {

    public static String STORAGE_PATH = "static";

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        STORAGE_PATH = fileName;

        SpringApplication.run(ChartographerApplication.class, args);
    }

}
