package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.ChartographerApplication;
import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import com.ablokhin.chartographer.exception.SizePositionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ChartaServiceImplTest {

    @Autowired
    private ChartaServiceImpl chartaServiceImpl;

    @BeforeAll
    public static void setup() throws IOException {
        String fileName = "static";
        Path path = Paths.get("static");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        ChartographerApplication.STORAGE_PATH = fileName;
    }

    @Test
    public void createChartaTest() throws ChartaNotFoundException, SizePositionException {
        Long expected = 1L;
        Long first = chartaServiceImpl.createCharta(1, 1);
        Long second = chartaServiceImpl.createCharta(6000, 6000);
        Long third = chartaServiceImpl.createCharta(1, 6000);
        assertEquals(1L, first);
        assertEquals(2L, second);
        assertEquals(3L, third);
        chartaServiceImpl.deleteCharta(1L);
        chartaServiceImpl.deleteCharta(2L);
        chartaServiceImpl.deleteCharta(3L);
    }

    @Test
    public void createWrongChartaTest() {
        assertThrows(SizePositionException.class, () -> {
            chartaServiceImpl.createCharta(0, 100);
        });
        assertThrows(SizePositionException.class, () -> {
            chartaServiceImpl.createCharta(-10, 10);
        });
        assertThrows(SizePositionException.class, () -> {
            chartaServiceImpl.createCharta(10, 0);
        });
        assertThrows(SizePositionException.class, () -> {
            chartaServiceImpl.createCharta(100, -100);
        });
    }
}
