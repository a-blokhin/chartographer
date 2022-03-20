package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.IntersectionException;
import com.ablokhin.chartographer.model.Charta;

public interface ConstraintService {
    boolean checkConstraint(int x, int minX, int maxX);

    void checkFragmentSizeConstraint(int width, int height) throws IntersectionException;

    void checkChartaSizeConstraint(int width, int height) throws IntersectionException;

    boolean checkIntersectionConstraint(int x, int y, int width, int height,
                                        Charta charta) throws IntersectionException;
}
