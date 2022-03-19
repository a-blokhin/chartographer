package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import com.ablokhin.chartographer.exception.IntersectionException;

import java.io.IOException;

public interface ChartaService {
    Long createCharta(Integer width, Integer height) throws IntersectionException, ChartaNotFoundException;

    void addFragment(Long id, byte[] postedFragment, Integer x, Integer y,
                     Integer width, Integer height) throws IOException, ChartaNotFoundException, IntersectionException;

    byte[] getFragment(Long id, Integer x, Integer y,
                       Integer width, Integer height) throws ChartaNotFoundException, IntersectionException, IOException;

    void deleteCharta(Long id) throws ChartaNotFoundException;
}
