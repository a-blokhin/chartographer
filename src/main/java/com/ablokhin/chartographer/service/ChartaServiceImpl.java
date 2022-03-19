package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.dao.Dao;
import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import com.ablokhin.chartographer.exception.IntersectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class ChartaServiceImpl implements ChartaService {

    public static final Integer FRAGMENT_MIN_WIDTH = 1;
    public static final Integer FRAGMENT_MAX_WIDTH = 5_000;
    public static final Integer FRAGMENT_MIN_HEIGHT = 1;
    public static final Integer FRAGMENT_MAX_HEIGHT = 5_000;
    public static final Integer CHARTA_MIN_WIDTH = 1;
    public static final Integer CHARTA_MAX_WIDTH = 20_000;
    public static final Integer CHARTA_MIN_HEIGHT = 1;
    public static final Integer CHARTA_MAX_HEIGHT = 50_000;

    private final Dao daoImpl;

    @Autowired
    public ChartaServiceImpl(@Qualifier("daoImpl") Dao dao) {
        this.daoImpl = dao;
    }

    private void checkConstraint(Integer x,
                                 Integer minX, Integer maxX) throws IntersectionException {
        if (x < minX) {
            throw new IntersectionException(x + " is less than " + minX);
        }
        if (x > maxX) {
            throw new IntersectionException(x + " is greater than " + maxX);
        }
    }

    private Integer newChartaSize(Integer fragmentCoordinate, Integer fragmentSize,
                                  Integer chartaSize) {
        int size = Math.min(fragmentSize, chartaSize);
        size = Math.min(size,
                (fragmentCoordinate > 0) ? (chartaSize - fragmentCoordinate) :
                        (chartaSize - fragmentCoordinate + chartaSize));
        return size;


    }

    @Override
    public Long createCharta(Integer width, Integer height) throws IntersectionException, ChartaNotFoundException {
        checkConstraint(width, CHARTA_MIN_WIDTH, CHARTA_MAX_WIDTH);
        checkConstraint(height, CHARTA_MIN_HEIGHT, CHARTA_MAX_HEIGHT);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        return daoImpl.createCharta(newImage);
    }

    @Override
    public void addFragment(Long id, byte[] postedFragment, Integer x, Integer y,
                            Integer width, Integer height) throws IOException, ChartaNotFoundException, IntersectionException {
        BufferedImage oldImage = daoImpl.getCharta(id);
        log.info(y + " " + height + " " + oldImage.getHeight());
        checkConstraint(x, -width + 1, oldImage.getWidth() - 1);
        checkConstraint(y, -height + 1, oldImage.getHeight() - 1);
        BufferedImage fragment = ImageIO.read(new ByteArrayInputStream(postedFragment));
        oldImage.createGraphics().drawImage(fragment, x, y, fragment.getWidth(), fragment.getHeight(), null);

        daoImpl.updateCharta(id, oldImage);
    }

    @Override
    public byte[] getFragment(Long id, Integer x, Integer y,
                              Integer width, Integer height) throws ChartaNotFoundException, IntersectionException, IOException {
        checkConstraint(width, FRAGMENT_MIN_WIDTH, FRAGMENT_MAX_WIDTH);
        checkConstraint(height, FRAGMENT_MIN_HEIGHT, FRAGMENT_MAX_HEIGHT);

        BufferedImage charta = daoImpl.getCharta(id);
        checkConstraint(x, -width + 1, charta.getWidth() - 1);
        checkConstraint(y, -height + 1, charta.getHeight() - 1);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        Integer newWidth = newChartaSize(x, width, charta.getWidth());
        Integer newHeight = newChartaSize(y, height, charta.getHeight());

        charta = charta.getSubimage(Math.max(0, x), Math.max(0, y), newWidth, newHeight);
        bufferedImage.createGraphics().drawImage(charta, -x, -y, width, height, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "BMP", baos);
        return baos.toByteArray();

    }

    @Override
    public void deleteCharta(Long id) throws ChartaNotFoundException {
        daoImpl.deleteCharta(id);
    }

}