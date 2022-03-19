package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import com.ablokhin.chartographer.exception.IntersectionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
public class ChartaServiceImplTest {

    private final ChartaService chartaServiceImpl = new ChartaServiceImpl(new DaoForServiceTest());

    private static byte[] bytes;
    private static final Long id = 1L;

    @BeforeAll
    public static void setup() throws IOException {
        BufferedImage fragment = new BufferedImage(101, 101, BufferedImage.TYPE_3BYTE_BGR);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(fragment, "BMP", baos);
        bytes = baos.toByteArray();
    }

    @Test
    public void createChartaTest() throws ChartaNotFoundException, IntersectionException {
        List<Integer> sizes = Arrays.asList(
                1, 1,
                6000, 6000,
                1, 6000);
        for (int i = 0; i < (sizes.size() / 2); i++) {
            assertEquals(i+1, chartaServiceImpl.createCharta(sizes.get(2 * i), sizes.get(2 * i + 1)));
        }
    }

    @Test
    public void createWrongChartaTest() {
        List<Integer> sizes = Arrays.asList(
                0, 100,
                -10, 10,
                10, 0,
                100, -100);
        for (int i = 0; i < (sizes.size() / 2); i++) {
            int finalI = i;
            assertThrows(IntersectionException.class, () ->
                    chartaServiceImpl.createCharta(sizes.get(2 * finalI), sizes.get(2 * finalI + 1)));
        }
    }

    @Test
    public void FragmentTest() throws ChartaNotFoundException, IntersectionException, IOException {

        List<Integer> sizes = Arrays.asList(
                -100, -100, 101, 101,
                -100, 999, 101, 101,
                999, -100, 101, 101,
                999, 999, 101, 101,
                200, 200, 101, 101
        );
        for (int i = 0; i < (sizes.size() / 4); i++) {
            log.info(i + " " +
                    sizes.get(4 * i) +
                    sizes.get(4 * i + 1) +
                    sizes.get(4 * i + 2) +
                    sizes.get(4 * i + 3));
            chartaServiceImpl.addFragment(id, bytes, sizes.get(4 * i),
                    sizes.get(4 * i + 1), sizes.get(4 * i + 2), sizes.get(4 * i + 3));
            chartaServiceImpl.getFragment(id, sizes.get(4 * i),
                    sizes.get(4 * i + 1), sizes.get(4 * i + 2), sizes.get(4 * i + 3));
        }
    }

    @Test
    public void addWrongFragmentTest() {
        List<Integer> sizes = Arrays.asList(
                -100, -100, 100, 100,
                -100, 999, 100, 100,
                999, -100, 100, 100,
                1000, 1000, 100, 100
        );
        for (int i = 0; i < (sizes.size() / 4); i++) {
            int finalI = i;
            assertThrows(IntersectionException.class, () -> chartaServiceImpl.addFragment(id, bytes, sizes.get(4 * finalI),
                    sizes.get(4 * finalI + 1), sizes.get(4 * finalI + 2), sizes.get(4 * finalI + 3)));
            assertThrows(IntersectionException.class, () -> chartaServiceImpl.getFragment(id, sizes.get(4 * finalI),
                    sizes.get(4 * finalI + 1), sizes.get(4 * finalI + 2), sizes.get(4 * finalI + 3)));
        }
    }

}
