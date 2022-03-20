package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.model.Charta;
import com.ablokhin.chartographer.storage.ImageDescriptionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Slf4j
@Component
public class ImageDescriptionStorageForServiceTest implements ImageDescriptionStorage {


    @Override
    public String createCharta(Integer width, Integer height) {
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
    public Boolean addFragment(String uid, Integer coordinateX, Integer coordinateY,
                               Integer width, Integer height, BufferedImage fragmentImage) {
        return true;
    }

    @Override
    public Boolean deleteCharta(String uid) {
        return true;
    }
}
