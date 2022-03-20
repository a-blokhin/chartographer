package com.ablokhin.chartographer.model;

public class Fragment {
    private final String id;
    private final int coordinateX;
    private final int coordinateY;
    private final int width;
    private final int height;

    public Fragment(String id, int coordinateX, int coordinateY, int width, int height) {
        this.id = id;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Fragment{" +
                "id='" + id + '\'' +
                ", CoordinateX=" + coordinateX +
                ", CoordinateY=" + coordinateY +
                ", Width=" + width +
                ", Height=" + height +
                '}';
    }

    public String getId() {
        return id;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
