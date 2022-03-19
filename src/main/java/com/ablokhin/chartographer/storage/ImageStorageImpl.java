package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.ablokhin.chartographer.ChartographerApplication.STORAGE_PATH;

@Component

public class ImageStorageImpl implements ImageStorage {

    @Override
    @Cacheable(cacheNames = "images", key = "#id")
    public BufferedImage getFragment(String id) throws FragmentNotFoundException {
        File file = new File(STORAGE_PATH + "/" + id + ".bmp");
        try {
            return ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
        } catch (IOException e) {
            throw new FragmentNotFoundException("Fragment not found during getting");
        }
    }

    @Override
    @CachePut(cacheNames = "images", key = "#id")
    public BufferedImage saveFragment(String id, BufferedImage image) throws FragmentNotFoundException {
        File file = new File(STORAGE_PATH + "/" + id + ".bmp");
        try {
            ImageIO.write(image, "BMP", file);
        } catch (IOException e) {
            throw new FragmentNotFoundException("Charta not found during update");
        }
        return image;
    }

    @Override
    @CacheEvict(cacheNames = "images", key = "#id")
    public Boolean deleteFragment(String id) throws FragmentNotFoundException {
        try {
            Files.delete(Paths.get(STORAGE_PATH + "/" + id + ".bmp"));
            return true;
        } catch (IOException e) {
            throw new FragmentNotFoundException("Charta not found during deletion");
        }
    }

}
