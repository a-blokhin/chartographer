package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.model.Charta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ablokhin.chartographer.ChartographerApplication.STORAGE_PATH;

@Component
public class DescriptionStorageImpl implements DescriptionStorage {

    private static final String DESCRIPTIONS_FOLDER = "charta_descriptions";
    private static final int PREFIX_SIZE = 2;

    private static Path getDescriptionPath(String uid) throws IOException {
        Path path = Paths.get(STORAGE_PATH, DESCRIPTIONS_FOLDER, uid.substring(0, PREFIX_SIZE));
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        return Paths.get(STORAGE_PATH, DESCRIPTIONS_FOLDER, uid.substring(0, PREFIX_SIZE), uid.substring(PREFIX_SIZE) + ".json");
    }

    private static Charta getChartaFromFile(File description) {
        try (Reader reader = new FileReader(description)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Charta.class);
        } catch (IOException ignored) {
        }
        return null;
    }

    @Override
    public Map<String, Charta> initDescriptions() {
        Map<String, Charta> oldChartas = new ConcurrentHashMap<>();
        Path path = Paths.get(STORAGE_PATH, DESCRIPTIONS_FOLDER);
        File[] prefixes = path.toFile().listFiles();

        if (Files.exists(path) && prefixes != null) {

            for (File prefix : prefixes) {
                File[] uids = prefix.listFiles();

                if (prefix.exists() && uids != null) {
                    for (File description : uids) {
                        Charta charta = getChartaFromFile(description);
                        if (charta != null) oldChartas.put(charta.getId(), charta);
                    }
                }
            }
        }
        return oldChartas;
    }

    @Override
    public void saveDescription(Charta charta) throws FragmentNotFoundException {
        try (BufferedWriter writer = Files.newBufferedWriter(getDescriptionPath(charta.getId()))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(charta));
        } catch (IOException e) {
            throw new FragmentNotFoundException("Charta not found during getting");
        }
    }

    @Override
    public void deleteDescription(String id) throws IOException {
        Files.delete(getDescriptionPath(id));
    }
}
