package com.ablokhin.chartographer.dao;

import com.ablokhin.chartographer.exception.ChartaNotFoundException;

import java.awt.image.BufferedImage;

public interface Dao {

    Long createCharta(BufferedImage image) throws ChartaNotFoundException;

    BufferedImage getCharta(Long id) throws ChartaNotFoundException;

    BufferedImage updateCharta(Long id, BufferedImage image) throws ChartaNotFoundException;

    void deleteCharta(Long id) throws ChartaNotFoundException;
}