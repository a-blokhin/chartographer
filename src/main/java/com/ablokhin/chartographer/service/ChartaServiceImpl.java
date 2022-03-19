package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.model.Charta;
import com.ablokhin.chartographer.model.Fragment;
import com.ablokhin.chartographer.storage.ImageDescriptionStorage;
import com.ablokhin.chartographer.storage.ImageStorage;
import com.ablokhin.chartographer.exception.FragmentNotFoundException;
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

import static com.ablokhin.chartographer.service.ConstraintService.*;

@Service
@Slf4j
public class ChartaServiceImpl implements ChartaService {

    @Autowired
    private final ImageStorage imageStorageImpl;
    @Autowired
    private ImageDescriptionStorage imageDescriptionStorage;

    @Autowired
    public ChartaServiceImpl(ImageStorage imageStorage, ImageDescriptionStorage imageDescriptionStorage) {
        this.imageStorageImpl = imageStorage;
        this.imageDescriptionStorage = imageDescriptionStorage;
    }


    private Integer newFragmentSize(Integer fragmentCoordinate,
                                    Integer fragmentSize, Integer chartaSize) {
        int size = Math.min(fragmentSize, chartaSize);
        log.info("Create fragment " + fragmentCoordinate + " " + fragmentSize + " " + chartaSize);
        size = Math.min(size, (fragmentCoordinate > 0) ?
                (chartaSize - fragmentCoordinate) : (fragmentSize + fragmentCoordinate));
        return size;
    }

    @Override
    public String createCharta(Integer width, Integer height) throws IntersectionException, FragmentNotFoundException {
        checkChartaSizeConstraint(width, height);
        return imageDescriptionStorage.createCharta(width, height);
    }

    @Override
    public boolean addFragment(String uid, byte[] postedFragment, Integer x, Integer y,
                               Integer width, Integer height) throws IOException, FragmentNotFoundException, IntersectionException {

        Charta charta = imageDescriptionStorage.getCharta(uid);
        checkIntersectionConstraint(x, y, width, height, charta);

        BufferedImage fragmentImage = ImageIO.read(new ByteArrayInputStream(postedFragment));
        int newWidth = newFragmentSize(x, width, charta.getWidth());
        int newHeight = newFragmentSize(y, height, charta.getHeight());
        fragmentImage = fragmentImage.getSubimage(Math.max(0, -x), Math.max(0, -y), newWidth, newHeight);

        return imageDescriptionStorage.addFragment(uid, Math.max(0, x), Math.max(0, y),
                newWidth, newHeight, fragmentImage);
    }

    @Override
    public byte[] getFragment(String uid, Integer x, Integer y,
                              Integer width, Integer height) throws FragmentNotFoundException, IntersectionException, IOException {

        checkFragmentSizeConstraint(width, height);
        Charta charta = imageDescriptionStorage.getCharta(uid);
        checkIntersectionConstraint(x, y, width, height, charta);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (Fragment fragment : charta.getFragments()) {
            if (checkConstraint(x, -width + 1, charta.getWidth() - 1) ||
                    checkConstraint(y, -height + 1, charta.getHeight() - 1)) {
                bufferedImage.createGraphics().drawImage(imageStorageImpl.getFragment(fragment.getId()),
                        -x + fragment.getCoordinateX(), -y + fragment.getCoordinateY(),
                        fragment.getWidth(), fragment.getHeight(), null);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "BMP", baos);
        return baos.toByteArray();

    }

    @Override
    public boolean deleteCharta(String uid) throws FragmentNotFoundException {
        return imageDescriptionStorage.deleteCharta(uid);
    }

}