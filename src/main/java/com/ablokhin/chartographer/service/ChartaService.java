package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import com.ablokhin.chartographer.exception.SizePositionException;

import java.io.IOException;

public interface ChartaService {
    Long createCharta(Integer width, Integer height) throws SizePositionException, ChartaNotFoundException;

    void createFragment(Long id, byte[] postedFragment, Integer x, Integer y,
                        Integer width, Integer height) throws IOException, ChartaNotFoundException, SizePositionException;

    byte[] getFragment(Long id, Integer x, Integer y,
                       Integer width, Integer height) throws ChartaNotFoundException, SizePositionException, IOException;

    void deleteCharta(Long id) throws ChartaNotFoundException;
}
