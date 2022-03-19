package com.ablokhin.chartographer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Charta {
    private final String id;
    private final Integer width;
    private final Integer height;
    private final List<Fragment> fragments = new ArrayList<>();

    public Charta(Integer width, Integer height) {
        id = UUID.randomUUID().toString();
        this.width = width;
        this.height = height;
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

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

}
