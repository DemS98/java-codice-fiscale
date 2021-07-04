package com.demetrio.codicefiscale.engine;

import com.demetrio.codicefiscale.exception.BirthPlaceHomonymyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

class CodiciCatastali {

    private static final Logger LOGGER = Logger.getLogger(CodiciCatastali.class.getName());

    private final Map<String, Map<String, String>> codes;

    private static class CodiciCatastaliHolder {
        static final CodiciCatastali INSTANCE = new CodiciCatastali();
    }

    private CodiciCatastali() {
        codes = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(Objects
                .requireNonNull(CodiciCatastali.class.getClassLoader()
                .getResourceAsStream("codici_catastali.csv"))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                if (split.length == 3) {
                    Map<String, String> provinceMap = codes.computeIfAbsent(split[1], k -> new HashMap<>());
                    provinceMap.put(split[2], split[0]);
                }
            }
        } catch (IOException | NullPointerException e) {
            LOGGER.log(Level.SEVERE, "Error in reading codici_catastali.csv", e);
        }
    }

    static CodiciCatastali getInstance() {
        return CodiciCatastaliHolder.INSTANCE;
    }

    String getCode(String birthPlace) {
        Map<String, String> provinceMap = codes.get(birthPlace.toUpperCase(Locale.ITALIAN));
        if (provinceMap != null) {
            if (provinceMap.size() > 1)
                throw new BirthPlaceHomonymyException();
            return provinceMap.values().stream().findFirst().orElse(null);
        }
        return null;
    }

    String getCode(String birthPlace, String birthPlaceProvince) {
        Map<String, String> provinceMap = codes.get(birthPlace.toUpperCase(Locale.ITALIAN));
        return provinceMap != null ? provinceMap.get(birthPlaceProvince.toUpperCase()) : null;
    }
}
