package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.ablokhin.chartographer.ChartographerApplication.STORAGE_PATH;

@Component
public class ImageStorageImpl implements ImageStorage {

    private static final String FRAGMENTS_FOLDER = "fragments";

    private static Path getFragmentPath(String uid) throws IOException {
        Path path = Paths.get(STORAGE_PATH, FRAGMENTS_FOLDER, uid.substring(0, 2));
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        return Paths.get(STORAGE_PATH, FRAGMENTS_FOLDER, uid.substring(0, 2), uid.substring(2) + ".bmp");
    }

    @Override
    @Cacheable(cacheNames = "images", key = "#uid")
    public BufferedImage getFragment(String uid) throws FragmentNotFoundException {
        try {
            return ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(getFragmentPath(uid))));
        } catch (IOException e) {
            throw new FragmentNotFoundException("Fragment not found during getting");
        }
    }

    @Override
    @CachePut(cacheNames = "images", key = "#uid")
    public BufferedImage saveFragment(String uid, BufferedImage image) throws FragmentNotFoundException {
        try {
            ImageIO.write(image, "BMP", getFragmentPath(uid).toFile());
        } catch (IOException e) {
            throw new FragmentNotFoundException("Charta not found during update");
        }
        return image;
    }

    @Override
    @CacheEvict(cacheNames = "images", key = "#id")
    public Boolean deleteFragment(String id) throws FragmentNotFoundException {
        try {
            Files.delete(getFragmentPath(id));
            return true;
        } catch (IOException e) {
            throw new FragmentNotFoundException("Charta not found during deletion");
        }
    }

}
