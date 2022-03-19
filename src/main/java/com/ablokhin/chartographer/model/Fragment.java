package com.ablokhin.chartographer.model;

import java.util.UUID;

public class Fragment {
    private final String id;
    private final Integer CoordinateX;
    private final Integer CoordinateY;
    private final Integer Width;
    private final Integer Height;

    public Fragment(Integer coordinateX, Integer coordinateY, Integer width, Integer height) {
        id = UUID.randomUUID().toString();
        CoordinateX = coordinateX;
        CoordinateY = coordinateY;
        Width = width;
        Height = height;
    }

    public String getId() {
        return id;
    }

    public Integer getCoordinateX() {
        return CoordinateX;
    }

    public Integer getCoordinateY() {
        return CoordinateY;
    }

    public Integer getWidth() {
        return Width;
    }

    public Integer getHeight() {
        return Height;
    }
}
