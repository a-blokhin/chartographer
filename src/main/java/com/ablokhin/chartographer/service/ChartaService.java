package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.exception.IntersectionException;

import java.io.IOException;

public interface ChartaService {
    String createCharta(int width, int height) throws IntersectionException, FragmentNotFoundException, IOException;

    void addFragment(String id, byte[] postedFragment, int x, int y,
                     int width, int height) throws IOException, FragmentNotFoundException, IntersectionException;

    byte[] getFragment(String uid, int x, int y,
                       int width, int height) throws FragmentNotFoundException, IntersectionException, IOException;

    void deleteCharta(String id) throws FragmentNotFoundException, IOException;
}
