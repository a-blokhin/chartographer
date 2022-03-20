package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.model.Charta;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageDescriptionStorage {
    String createCharta(Integer width, Integer height) throws IOException, FragmentNotFoundException;

    Charta getCharta(String uid) throws FragmentNotFoundException;

    BufferedImage getFragment(String uid) throws FragmentNotFoundException;

    Boolean addFragment(String uid,
                        Integer coordinateX, Integer coordinateY, Integer width, Integer height,
                        BufferedImage fragmentImage) throws FragmentNotFoundException, IOException;

    Boolean deleteCharta(String uid) throws FragmentNotFoundException, IOException;
}
