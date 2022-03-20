package com.ablokhin.chartographer.model;

import java.util.ArrayList;
import java.util.List;

public class Charta {
    private final String id;
    private final int width;
    private final int height;
    private final List<Fragment> fragments = new ArrayList<>();

    public Charta(String id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Charta{" +
                "id='" + id + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", fragments=" + fragments +
                '}';
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
