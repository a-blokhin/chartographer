package com.ablokhin.chartographer.dao;

import com.ablokhin.chartographer.exception.ChartaNotFoundException;
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
import java.util.concurrent.atomic.AtomicLong;

import static com.ablokhin.chartographer.ChartographerApplication.STORAGE_PATH;

@Component
public class DaoImpl implements Dao {

    private static final AtomicLong currentId = new AtomicLong(0);

    @Override
    public Long createCharta(BufferedImage image) throws ChartaNotFoundException {
        long id = currentId.addAndGet(1);
        File file = new File(STORAGE_PATH + "/" + id + ".bmp");
        try {
            ImageIO.write(image, "BMP", file);
        } catch (IOException e) {
            throw new ChartaNotFoundException("Charta not found during create");
        }
        return id;
    }

    @Override
    @Cacheable(cacheNames = "images", key="#id")
    public BufferedImage getCharta(Long id) throws ChartaNotFoundException {
        File file = new File(STORAGE_PATH + "/" + id + ".bmp");
        try {
            return ImageIO.read(new ByteArrayInputStream(
                    Files.readAllBytes(file.toPath())));
        } catch (IOException e) {
            throw new ChartaNotFoundException("Charta not found during getting");
        }
    }

    @Override
    @CachePut(cacheNames = "images", key="#id")
    public BufferedImage updateCharta(Long id, BufferedImage image) throws ChartaNotFoundException {
        File file = new File(STORAGE_PATH + "/" + id + ".bmp");
        try {
            ImageIO.write(image, "BMP", file);
        } catch (IOException e) {
            throw new ChartaNotFoundException("Charta not found during update");
        }
        return image;
    }

    @Override
    @CacheEvict(cacheNames = "images", key="#id")
    public void deleteCharta(Long id) throws ChartaNotFoundException {
        try {
            Files.delete(Paths.get(STORAGE_PATH + "/" + id + ".bmp"));
        } catch (IOException e) {
            throw new ChartaNotFoundException("Charta not found during deletion");
        }
    }

}
