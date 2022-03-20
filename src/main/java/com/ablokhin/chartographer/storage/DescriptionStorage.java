package com.ablokhin.chartographer.storage;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.model.Charta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface DescriptionStorage {
    default Map<String, Charta> initDescriptions() {
        return new HashMap<>();
    }

    void saveDescription(Charta charta) throws FragmentNotFoundException;

    void deleteDescription(String id) throws IOException;
}
