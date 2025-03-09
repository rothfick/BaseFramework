package com.peliQAn.framework.config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton class to manage properties from configuration files
 */
@Slf4j
public class PropertyManager {
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";
    private static PropertyManager instance;
    private final Properties properties;

    private PropertyManager() {
        properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
            log.info("Loaded configuration properties from: {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            log.error("Failed to load properties file: {}", e.getMessage());
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public static synchronized PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        String property = properties.getProperty(key);
        if (property == null) {
            log.warn("Property '{}' not found in configuration file", key);
        }
        return property;
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public int getIntProperty(String key, int defaultValue) {
        String property = getProperty(key);
        return (property != null) ? Integer.parseInt(property) : defaultValue;
    }

    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String property = getProperty(key);
        return (property != null) ? Boolean.parseBoolean(property) : defaultValue;
    }
}