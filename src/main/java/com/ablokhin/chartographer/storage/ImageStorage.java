package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;

import java.awt.image.BufferedImage;

public interface ImageStorage {

    BufferedImage getFragment(String id) throws FragmentNotFoundException;

    BufferedImage saveFragment(String id, BufferedImage image) throws FragmentNotFoundException;

    Boolean deleteFragment(String id) throws FragmentNotFoundException;
}