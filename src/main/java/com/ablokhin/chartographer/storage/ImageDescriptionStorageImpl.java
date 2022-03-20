package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.model.Charta;
import com.ablokhin.chartographer.model.Fragment;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.ablokhin.chartographer.ChartographerApplication.STORAGE_PATH;

@Slf4j
@Component
public class ImageDescriptionStorageImpl implements ImageDescriptionStorage {

    private final ImageStorage imageStorageImpl;

    private static final Map<String, Charta> chartas = initChartas();

    public ImageDescriptionStorageImpl(@Qualifier("imageStorageImpl") ImageStorage imageStorageImpl) {
        this.imageStorageImpl = imageStorageImpl;
    }

    private static Map<String, Charta> initChartas() {
        Map<String, Charta> oldChartas = new HashMap<>();
        Path path = Paths.get(STORAGE_PATH, "charta_descriptions");
        File[] files = path.toFile().listFiles();
        if (Files.exists(path) &&  files != null) {
            for (File description: files){
                Gson gson = new Gson();
                try (Reader reader = new FileReader(description)) {
                    Charta charta = gson.fromJson(reader, Charta.class);
                    oldChartas.put(charta.getId(), charta);
                } catch (IOException ignored) {}
            }
        }
        return oldChartas;
    }

    private static Path getDescriptionPath(String uid) throws IOException {
        Path path = Paths.get(STORAGE_PATH, "charta_descriptions");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        return Paths.get(STORAGE_PATH, "charta_descriptions", uid + ".json");
    }

    private static void saveDescription(Charta charta) throws FragmentNotFoundException {
        try (BufferedWriter writer = Files.newBufferedWriter(getDescriptionPath(charta.getId()))) {
            Gson gson = new Gson();
            writer.write(gson.toJson(charta));
        } catch (IOException e) {
            throw new FragmentNotFoundException("Charta not found during getting");
        }
    }


    @Override
    public String createCharta(Integer width, Integer height) throws FragmentNotFoundException {
        String uid = UUID.randomUUID().toString();
        Charta charta = new Charta(uid, width, height);

        chartas.put(uid, charta);
        saveDescription(charta);
        log.info("save Description");

        return uid;
    }

    @Override
    public Charta getCharta(String uid) throws FragmentNotFoundException {
        Charta charta = chartas.get(uid);
        if (charta != null) {
            return charta;
        } else {
            throw new FragmentNotFoundException("Charta not found during getting");
        }
    }

    @Override
    public BufferedImage getFragment(String uid) throws FragmentNotFoundException {
        return imageStorageImpl.getFragment(uid);
    }

    @Override
    public Boolean addFragment(String uid,
                               Integer coordinateX, Integer coordinateY, Integer width, Integer height,
                               BufferedImage fragmentImage) throws FragmentNotFoundException {
        String fragmentUid = UUID.randomUUID().toString();
        Fragment fragment = new Fragment(fragmentUid, coordinateX, coordinateY, width, height);
        Charta charta = getCharta(uid);

        imageStorageImpl.saveFragment(fragment.getId(), fragmentImage);
        charta.addFragment(fragment);
        chartas.put(uid, charta);
        saveDescription(charta);

        return true;
    }

    @Override
    public Boolean deleteCharta(String uid) throws FragmentNotFoundException, IOException {
        Charta charta = getCharta(uid);
        log.info("found Charta");
        chartas.remove(uid);
        log.info("delete in hashmap");
        for (Fragment fragment : charta.getFragments()) {
            imageStorageImpl.deleteFragment(fragment.getId());
        }
        Files.delete(getDescriptionPath(uid));
        return true;
    }
}
