package com.ablokhin.chartographer.dao;

import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.awt.image.BufferedImage;

public interface Dao {
    byte[] getCharta(Long id) throws ChartaNotFoundException;

    byte[] saveCharta(Long id, BufferedImage charta) throws ChartaNotFoundException;

    void deleteCharta(Long id) throws ChartaNotFoundException;
}
