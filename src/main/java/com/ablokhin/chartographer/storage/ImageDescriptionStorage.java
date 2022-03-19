package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.model.Fragment;
import com.ablokhin.chartographer.model.Charta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.ablokhin.chartographer.service.ConstraintService.checkConstraint;

@Component
@Slf4j
public class ImageDescriptionStorage {

    @Autowired
    @Qualifier("imageStorageImpl")
    private final ImageStorage imageStorageImpl;

    private Map<String, Charta> chartas = new HashMap<>();

    @Autowired
    public ImageDescriptionStorage(ImageStorage imageStorageImpl) {
        this.imageStorageImpl = imageStorageImpl;
    }

    public String createCharta(Integer width, Integer height) {
        Charta charta = new Charta(width, height);
        chartas.put(charta.getId(), charta);
        log.info("Create charta " + charta.getId());
        return charta.getId();
    }

    public Charta getCharta(String uid) throws FragmentNotFoundException {
        Charta charta = chartas.get(uid);
        if (charta != null) {
            return charta;
        } else {
            throw new FragmentNotFoundException("Charta not found during getting");
        }
    }

    public Boolean addFragment(String uid,
                               Integer coordinateX, Integer coordinateY, Integer width, Integer height,
                               BufferedImage fragmentImage) throws FragmentNotFoundException {
        Charta charta = getCharta(uid);
        Fragment fragment = new Fragment(coordinateX, coordinateY, width, height);
        imageStorageImpl.saveFragment(fragment.getId(), fragmentImage);
        charta.addFragment(fragment);
        chartas.put(uid, charta);
        log.info("Create fragment " + fragment.getId());
        return true;
    }

    public Boolean deleteCharta(String uid) throws FragmentNotFoundException {
        Charta charta = getCharta(uid);
        chartas.keySet().removeIf(key -> Objects.equals(key, uid));
        for (Fragment fragment : charta.getFragments()) {
            imageStorageImpl.deleteFragment(fragment.getId());
        }
        return true;
    }
}
