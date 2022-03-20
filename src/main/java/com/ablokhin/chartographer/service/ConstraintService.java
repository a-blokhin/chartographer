package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.IntersectionException;
import com.ablokhin.chartographer.model.Charta;

public interface ConstraintService {
    boolean checkConstraint(Integer x, Integer minX, Integer maxX);

    void checkFragmentSizeConstraint(Integer width, Integer height) throws IntersectionException;

    void checkChartaSizeConstraint(Integer width, Integer height) throws IntersectionException;

    void checkIntersectionConstraint(Integer x, Integer y, Integer width, Integer height,
                                     Charta charta) throws IntersectionException;
}
