package org.fundacionjala.bdd.api;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class PropReader {

    private static final Logger LOGGER = LogManager.getLogger(PropReader.class);
    private Properties properties;

    public PropReader(final String path) {
        init(path);
    }

    public String getEnv(final String propName) {
        String property = System.getProperty(propName);
        return Objects.isNull(property) ? properties.getProperty(propName) : "";
    }

    private void init(final String path) {
        try (InputStreamReader inputFile = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)) {
            this.properties = new Properties();
            this.properties.load(inputFile);
        } catch (IOException e) {
            LOGGER.error("Error when reading properties file.");
            LOGGER.error(e.getMessage());
        }
    }
}
