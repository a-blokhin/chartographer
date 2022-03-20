package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
public class ChartaServiceImplTest {

    private final ChartaService chartaServiceImpl = new ChartaServiceImpl(new ImageDescriptionStorageForServiceTest(),
            new ConstraintServiceImpl());

    private static byte[] bytes;
    private static final String id = UUID.randomUUID().toString();

    @BeforeAll
    public static void setup() throws IOException {
        BufferedImage fragment = new BufferedImage(101, 101, BufferedImage.TYPE_3BYTE_BGR);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(fragment, "BMP", baos);
        bytes = baos.toByteArray();
    }

    @Test
    public void createCharta_CorrectData_NotThrowExceptions() throws IntersectionException, FragmentNotFoundException, IOException {
        List<Integer> sizes = Arrays.asList(
                1, 1,
                6000, 6000,
                1, 6000);
        for (int i = 0; i < (sizes.size() / 2); i++) {
            assertEquals("c2246938-b196-45d1-a835-7e1f682b0e16",
                    chartaServiceImpl.createCharta(sizes.get(2 * i), sizes.get(2 * i + 1)));
        }
    }

    @Test
    public void createCharta_WrongData_ThrowExceptions() {
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
    public void addGetFragment_CorrectData_NotThrowExceptions() throws IntersectionException, IOException, FragmentNotFoundException {

        List<Integer> sizes = Arrays.asList(
                -100, -100, 101, 101,
                -100, 999, 101, 101,
                999, -100, 101, 101,
                999, 999, 101, 101,
                200, 200, 101, 101
        );
        for (int i = 0; i < (sizes.size() / 4); i++) {
            chartaServiceImpl.addFragment(id, bytes, sizes.get(4 * i),
                    sizes.get(4 * i + 1), sizes.get(4 * i + 2), sizes.get(4 * i + 3));
            chartaServiceImpl.getFragment(id, sizes.get(4 * i),
                    sizes.get(4 * i + 1), sizes.get(4 * i + 2), sizes.get(4 * i + 3));
        }
    }

    @Test
    public void addGetFragment_WrongData_ThrowExceptions() {
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
