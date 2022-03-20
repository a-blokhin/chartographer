package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.model.Charta;
import com.ablokhin.chartographer.storage.ImageDescriptionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Slf4j
@Component
public class ImageDescriptionStorageForServiceTest implements ImageDescriptionStorage {


    @Override
    public String createCharta(int width, int height) {
        return "c2246938-b196-45d1-a835-7e1f682b0e16";
    }

    @Override
    public Charta getCharta(String uid) {
        return new Charta(uid, 1000, 1000);
    }

    @Override
    public BufferedImage getFragment(String uid) {
        return new BufferedImage(1000, 1000, BufferedImage.TYPE_3BYTE_BGR);
    }

    @Override
    public void addFragment(String uid, int coordinateX, int coordinateY,
                            int width, int height, BufferedImage fragmentImage) {
    }

    @Override
    public void deleteCharta(String uid) {
    }
}
