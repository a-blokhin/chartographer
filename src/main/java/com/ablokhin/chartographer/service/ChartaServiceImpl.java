package com.ablokhin.chartographer.service;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.exception.IntersectionException;
import com.ablokhin.chartographer.model.Charta;
import com.ablokhin.chartographer.model.Fragment;
import com.ablokhin.chartographer.storage.ImageDescriptionStorage;
import com.ablokhin.chartographer.storage.ImageStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
@Slf4j
public class ChartaServiceImpl implements ChartaService {

    private final ImageDescriptionStorage imageDescriptionStorageImpl;
    private final ConstraintService constraintServiceImpl;


    @Autowired
    public ChartaServiceImpl(ImageDescriptionStorage imageDescriptionStorageImpl, ConstraintService constraintServiceImpl) {
        this.imageDescriptionStorageImpl = imageDescriptionStorageImpl;
        this.constraintServiceImpl = constraintServiceImpl;
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
    public String createCharta(Integer width, Integer height) throws IntersectionException, FragmentNotFoundException, IOException {
        constraintServiceImpl.checkChartaSizeConstraint(width, height);
        return imageDescriptionStorageImpl.createCharta(width, height);
    }

    @Override
    public boolean addFragment(String uid, byte[] postedFragment, Integer x, Integer y,
                               Integer width, Integer height) throws IOException, FragmentNotFoundException, IntersectionException {

        Charta charta = imageDescriptionStorageImpl.getCharta(uid);
        log.info("getted charta" + charta);
        constraintServiceImpl.checkIntersectionConstraint(x, y, width, height, charta);

        BufferedImage fragmentImage = ImageIO.read(new ByteArrayInputStream(postedFragment));
        int newWidth = newFragmentSize(x, width, charta.getWidth());
        int newHeight = newFragmentSize(y, height, charta.getHeight());
        fragmentImage = fragmentImage.getSubimage(Math.max(0, -x), Math.max(0, -y), newWidth, newHeight);

        return imageDescriptionStorageImpl.addFragment(uid, Math.max(0, x), Math.max(0, y),
                newWidth, newHeight, fragmentImage);
    }

    @Override
    public byte[] getFragment(String uid, Integer x, Integer y,
                              Integer width, Integer height) throws FragmentNotFoundException, IntersectionException, IOException {

        constraintServiceImpl.checkFragmentSizeConstraint(width, height);
        Charta charta = imageDescriptionStorageImpl.getCharta(uid);
        constraintServiceImpl.checkIntersectionConstraint(x, y, width, height, charta);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (Fragment fragment : charta.getFragments()) {
            if (constraintServiceImpl.checkConstraint(x, -width + 1, charta.getWidth() - 1) ||
                    constraintServiceImpl.checkConstraint(y, -height + 1, charta.getHeight() - 1)) {
                bufferedImage.createGraphics().drawImage(imageDescriptionStorageImpl.getFragment(fragment.getId()),
                        -x + fragment.getCoordinateX(), -y + fragment.getCoordinateY(),
                        fragment.getWidth(), fragment.getHeight(), null);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "BMP", baos);
        return baos.toByteArray();

    }

    @Override
    public boolean deleteCharta(String uid) throws FragmentNotFoundException, IOException {
        return imageDescriptionStorageImpl.deleteCharta(uid);
    }

}