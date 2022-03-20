package com.ablokhin.chartographer.model;

public class Fragment {
    private final String id;
    private final Integer CoordinateX;
    private final Integer CoordinateY;
    private final Integer Width;
    private final Integer Height;

    public Fragment(String id, Integer coordinateX, Integer coordinateY, Integer width, Integer height) {
        this.id = id;
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
