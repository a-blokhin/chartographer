package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.model.Charta;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageDescriptionStorage {
    String createCharta(int width, int height) throws IOException, FragmentNotFoundException;

    Charta getCharta(String uid) throws FragmentNotFoundException;

    BufferedImage getFragment(String uid) throws FragmentNotFoundException;

    void addFragment(String uid,
                     int coordinateX, int coordinateY, int width, int height,
                     BufferedImage fragmentImage) throws FragmentNotFoundException, IOException;

    void deleteCharta(String uid) throws FragmentNotFoundException, IOException;
}
