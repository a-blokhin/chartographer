package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.dao.DaoImpl;
import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import com.ablokhin.chartographer.exception.SizePositionException;
import com.ablokhin.chartographer.model.Charta;
import com.ablokhin.chartographer.model.Fragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ChartaServiceImpl implements ChartaService {

    @Autowired
    private DaoImpl daoImpl;

    private void checkConstraint(Integer x,
                                 Integer minX, Integer maxX) throws SizePositionException {
        if (x < minX) {
            throw new SizePositionException(x + "is less than" + minX);
        }
        if (x > maxX) {
            throw new SizePositionException(x + "is greater than" + maxX);
        }
    }

    private Integer newSize(Integer fragmentCoordinate, Integer fragmentSize,
                            Integer chartaSize) {
        int size = Math.min(fragmentSize, chartaSize);
        size = Math.min(size,
                (fragmentCoordinate > 0) ? (chartaSize - fragmentCoordinate) :
                        (chartaSize - fragmentCoordinate + chartaSize));
        return size;


    }

    @Override
    public Long createCharta(Integer width, Integer height) throws SizePositionException, ChartaNotFoundException {
        checkConstraint(width, Charta.minWidth, Charta.maxWidth);
        checkConstraint(height, Charta.minHeight, Charta.maxHeight);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Charta charta = new Charta(width, height);

        daoImpl.saveCharta(charta.getId(), bufferedImage);
        return charta.getId();
    }

    @Override
    public void createFragment(Long id, byte[] postedFragment, Integer x, Integer y,
                               Integer width, Integer height) throws IOException, ChartaNotFoundException, SizePositionException {
        BufferedImage charta = ImageIO.read(new ByteArrayInputStream(daoImpl.getCharta(id)));
        checkConstraint(x, -width + 1, charta.getWidth() - 1);
        checkConstraint(y, -height + 1, charta.getHeight() - 1);
        BufferedImage fragment = ImageIO.read(new ByteArrayInputStream(postedFragment));
        charta.createGraphics().drawImage(fragment, x, y, fragment.getWidth(), fragment.getHeight(), null);
        daoImpl.saveCharta(id, charta);
    }

    @Override
    public byte[] getFragment(Long id, Integer x, Integer y,
                              Integer width, Integer height) throws ChartaNotFoundException, SizePositionException, IOException {
        checkConstraint(width, Fragment.minWidth, Fragment.maxWidth);
        checkConstraint(height, Fragment.minHeight, Fragment.maxHeight);

        BufferedImage charta = ImageIO.read(new ByteArrayInputStream(daoImpl.getCharta(id)));

        checkConstraint(x, -width + 1, charta.getWidth() - 1);
        checkConstraint(y, -height + 1, charta.getHeight() - 1);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        Integer newWidth = newSize(x, width, charta.getWidth());
        Integer newHeight = newSize(y, height, charta.getHeight());

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