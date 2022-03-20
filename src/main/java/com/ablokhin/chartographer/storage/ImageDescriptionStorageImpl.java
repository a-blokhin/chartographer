package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.model.Charta;
import com.ablokhin.chartographer.model.Fragment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class ImageDescriptionStorageImpl implements ImageDescriptionStorage {

    private final DescriptionStorage descriptionStorageImpl;
    private final ImageStorage imageStorageImpl;
    private final Map<String, Charta> chartas;

    public ImageDescriptionStorageImpl(@Qualifier("imageStorageImpl") ImageStorage imageStorageImpl,
                                       @Qualifier("descriptionStorageImpl") DescriptionStorage descriptionStorageImpl) {
        this.imageStorageImpl = imageStorageImpl;
        this.descriptionStorageImpl = descriptionStorageImpl;
        chartas = descriptionStorageImpl.initDescriptions();
    }

    @Override
    public String createCharta(int width, int height) throws FragmentNotFoundException {

        String uid = UUID.randomUUID().toString();
        Charta charta = new Charta(uid, width, height);

        chartas.put(uid, charta);
        descriptionStorageImpl.saveDescription(charta);

        log.info("save charta {}", charta);
        return uid;
    }

    @Override
    public Charta getCharta(String uid) throws FragmentNotFoundException {

        Charta charta = chartas.get(uid);
        if (charta != null) {
            return charta;
        }

        throw new FragmentNotFoundException("Charta not found during getting");
    }

    @Override
    public BufferedImage getFragment(String uid) throws FragmentNotFoundException {
        return imageStorageImpl.getFragment(uid);
    }

    @Override
    public void addFragment(String uid,
                            int coordinateX, int coordinateY, int width, int height,
                            BufferedImage fragmentImage) throws FragmentNotFoundException {

        String fragmentUid = UUID.randomUUID().toString();
        Fragment fragment = new Fragment(fragmentUid, coordinateX, coordinateY, width, height);
        Charta charta = getCharta(uid);
        charta.addFragment(fragment);

        imageStorageImpl.saveFragment(fragment.getId(), fragmentImage);
        chartas.put(uid, charta);
        descriptionStorageImpl.saveDescription(charta);

        log.info("save fragment {} to charta with uid = {}", fragment, uid);
    }

    @Override
    public void deleteCharta(String uid) throws FragmentNotFoundException, IOException {

        Charta charta = getCharta(uid);

        chartas.remove(uid);
        for (Fragment fragment : charta.getFragments()) {
            imageStorageImpl.deleteFragment(fragment.getId());
        }
        descriptionStorageImpl.deleteDescription(uid);

        log.info("delete charta with uid = {}", uid);
    }
}
