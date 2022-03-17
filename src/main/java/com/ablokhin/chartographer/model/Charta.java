package com.ablokhin.chartographer.model;


import java.util.concurrent.atomic.AtomicLong;

public class Charta {
    private Long id;
    private Integer width;
    private Integer height;
    public static final Integer minWidth = 1;
    public static final Integer maxWidth = 20_000;
    public static final Integer minHeight = 1;
    public static final Integer maxHeight = 50_000;
    private static volatile AtomicLong currentId = new AtomicLong(0);

    public Charta(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        this.id = currentId.addAndGet(1);
    }

    public Charta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
