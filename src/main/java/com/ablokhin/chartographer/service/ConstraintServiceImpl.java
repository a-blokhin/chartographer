package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.IntersectionException;
import com.ablokhin.chartographer.model.Charta;
import org.springframework.stereotype.Component;

@Component
public class ConstraintServiceImpl implements ConstraintService {

    public static final int FRAGMENT_MIN_WIDTH = 1;
    public static final int FRAGMENT_MAX_WIDTH = 5_000;
    public static final int FRAGMENT_MIN_HEIGHT = 1;
    public static final int FRAGMENT_MAX_HEIGHT = 5_000;
    public static final int CHARTA_MIN_WIDTH = 1;
    public static final int CHARTA_MAX_WIDTH = 20_000;
    public static final int CHARTA_MIN_HEIGHT = 1;
    public static final int CHARTA_MAX_HEIGHT = 50_000;

    @Override
    public boolean checkConstraint(int x, int minX, int maxX) {
        return ((x >= minX) && (x <= maxX));
    }

    @Override
    public void checkFragmentSizeConstraint(int width, int height) throws IntersectionException {
        if (!(checkConstraint(width, FRAGMENT_MIN_WIDTH, FRAGMENT_MAX_WIDTH) &&
                checkConstraint(height, FRAGMENT_MIN_HEIGHT, FRAGMENT_MAX_HEIGHT))) {
            throw new IntersectionException("width", width, FRAGMENT_MIN_WIDTH, FRAGMENT_MAX_WIDTH,
                    "height", height, FRAGMENT_MIN_HEIGHT, FRAGMENT_MAX_HEIGHT);
        }
    }

    @Override
    public void checkChartaSizeConstraint(int width, int height) throws IntersectionException {
        if (!(checkConstraint(width, CHARTA_MIN_WIDTH, CHARTA_MAX_WIDTH) &&
                checkConstraint(height, CHARTA_MIN_HEIGHT, CHARTA_MAX_HEIGHT))) {
            throw new IntersectionException("width", width, CHARTA_MIN_WIDTH, CHARTA_MAX_WIDTH,
                    "height", height, CHARTA_MIN_HEIGHT, CHARTA_MAX_HEIGHT);
        }
    }

    @Override
    public boolean checkIntersectionConstraint(int x, int y, int width, int height,
                                               Charta charta) throws IntersectionException {
        if (!(checkConstraint(x, -width + 1, charta.getWidth() - 1) &&
                checkConstraint(y, -height + 1, charta.getHeight() - 1))) {
            throw new IntersectionException("x", x, (-width + 1), (charta.getWidth() - 1),
                    "y", y, (-height + 1), (charta.getHeight() - 1));
        }
        return true;
    }
}
