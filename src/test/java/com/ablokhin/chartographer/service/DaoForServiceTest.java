package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.dao.Dao;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DaoForServiceTest implements Dao {

    private static final AtomicLong currentId = new AtomicLong(0);
    @Override
    public BufferedImage getCharta(Long id){
        return new BufferedImage(1000, 1000, BufferedImage.TYPE_3BYTE_BGR);
    }

    @Override
    public Long createCharta(BufferedImage image){
        return currentId.addAndGet(1);
    }

    @Override
    public BufferedImage updateCharta(Long id, BufferedImage image){
        return new BufferedImage(1000, 1000, BufferedImage.TYPE_3BYTE_BGR);
    }

    @Override
    public void deleteCharta(Long id){
    }
}
