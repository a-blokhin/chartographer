package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.IntersectionException;
import com.ablokhin.chartographer.model.Charta;

public class ConstraintService {

    public static final Integer FRAGMENT_MIN_WIDTH = 1;
    public static final Integer FRAGMENT_MAX_WIDTH = 5_000;
    public static final Integer FRAGMENT_MIN_HEIGHT = 1;
    public static final Integer FRAGMENT_MAX_HEIGHT = 5_000;
    public static final Integer CHARTA_MIN_WIDTH = 1;
    public static final Integer CHARTA_MAX_WIDTH = 20_000;
    public static final Integer CHARTA_MIN_HEIGHT = 1;
    public static final Integer CHARTA_MAX_HEIGHT = 50_000;

    public static boolean checkConstraint(Integer x, Integer minX, Integer maxX) {
        return !((x < minX) || (x > maxX));
    }

    public static void checkFragmentSizeConstraint(Integer width, Integer height) throws IntersectionException {
        if (!(checkConstraint(width, FRAGMENT_MIN_WIDTH, FRAGMENT_MAX_WIDTH) ||
                checkConstraint(height, FRAGMENT_MIN_HEIGHT, FRAGMENT_MAX_HEIGHT))) {
            throw new IntersectionException("width", width, FRAGMENT_MIN_WIDTH, FRAGMENT_MAX_WIDTH,
                    "height", height, FRAGMENT_MIN_HEIGHT, FRAGMENT_MAX_HEIGHT);
        }
    }

    public static void checkChartaSizeConstraint(Integer width, Integer height) throws IntersectionException {
        if (!(checkConstraint(width, CHARTA_MIN_WIDTH, CHARTA_MAX_WIDTH) ||
                checkConstraint(height, CHARTA_MIN_HEIGHT, CHARTA_MAX_HEIGHT))) {
            throw new IntersectionException("width", width, CHARTA_MIN_WIDTH, CHARTA_MAX_WIDTH,
                    "height", height, CHARTA_MIN_HEIGHT, CHARTA_MAX_HEIGHT);
        }
    }

    public static void checkIntersectionConstraint(Integer x, Integer y, Integer width, Integer height,
                                                   Charta charta) throws IntersectionException {
        if (!(checkConstraint(x, -width + 1, charta.getWidth() - 1) ||
                checkConstraint(y, -height + 1, charta.getHeight() - 1))) {
            throw new IntersectionException("x", x, (-width + 1), (charta.getWidth() - 1),
                    "y", y, (-height + 1), (charta.getHeight() - 1));
        }
    }
}
