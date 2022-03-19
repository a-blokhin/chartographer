package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.storage.ImageStorage;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

//@Component
//public class ImageStorageForServiceTest implements ImageStorage {
//
//    @Override
//    public String createCharta(BufferedImage image) {
//        return UUID.randomUUID().toString();
//    }
//
//    @Override
//    public BufferedImage getFragment(String id) {
//        return new BufferedImage(1000, 1000, BufferedImage.TYPE_3BYTE_BGR);
//    }
//
//    @Override
//    public BufferedImage saveFragment(String id, BufferedImage image) {
//        return new BufferedImage(1000, 1000, BufferedImage.TYPE_3BYTE_BGR);
//    }
//
//    @Override
//    public void deleteFragment(Long id) {
//
//    }
//}
