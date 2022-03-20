package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.exception.IntersectionException;

import java.io.IOException;

public interface ChartaService {
    String createCharta(Integer width, Integer height) throws IntersectionException, FragmentNotFoundException, IOException;

    boolean addFragment(String id, byte[] postedFragment, Integer x, Integer y,
                        Integer width, Integer height) throws IOException, FragmentNotFoundException, IntersectionException;

    byte[] getFragment(String uid, Integer x, Integer y,
                       Integer width, Integer height) throws FragmentNotFoundException, IntersectionException, IOException;

    boolean deleteCharta(String id) throws FragmentNotFoundException, IOException;
}
