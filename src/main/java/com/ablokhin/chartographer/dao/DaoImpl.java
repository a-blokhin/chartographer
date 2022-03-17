package com.ablokhin.chartographer.dao;

import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.ablokhin.chartographer.ChartographerApplication.STORAGE_PATH;

@Service
public class DaoImpl implements Dao {

    @Override
    @Cacheable(cacheNames = "images", key="#id")
    public byte[] getCharta(Long id) throws ChartaNotFoundException {
        File file = new File(STORAGE_PATH + "/" + id + ".bmp");
        try {
            return  Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new ChartaNotFoundException("Charta not found during reading");
        }
    }

    @Override
    @CachePut(cacheNames = "images", key="#id")
    public byte[] saveCharta(Long id, BufferedImage charta) throws ChartaNotFoundException {
        File file = new File(STORAGE_PATH + "/" + id + ".bmp");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(charta, "BMP", file);
            ImageIO.write(charta, "BMP", baos);
        } catch (IOException e) {
            throw new ChartaNotFoundException("Charta not found during save");
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
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
