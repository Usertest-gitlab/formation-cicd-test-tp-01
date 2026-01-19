package com.devops.cicd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PricingConfigLoader {

    private static final String CONFIG_FILE = "app.properties";

    public PricingConfig load() {
        Properties props = new Properties();
        
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new RuntimeException("Fichier de configuration introuvable : " + CONFIG_FILE);
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de la configuration", e);
        }

        double vatRate = Double.parseDouble(required(props, "vatRate"));
        double freeShippingThreshold = Double.parseDouble(required(props, "freeShippingThreshold"));

        return new PricingConfig(vatRate, freeShippingThreshold);
    }

    private String required(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Clé manquante dans le fichier properties : " + key);
        }
        return value;
    }
}