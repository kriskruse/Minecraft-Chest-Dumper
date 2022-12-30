package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Properties defaultValues = new Properties();
    private static final Logger Logger = LogUtils.getLogger();
    private String fileName;
    private String lootKey;



    Config(String fileName) {
        this.fileName = fileName;
    }

    public void read() {
        // Create a new properties object, with the default values from bottom of this file
        Properties properties = new Properties(defaultValues);

        // Try to load the config file
        try {
            FileReader configReader = new FileReader(fileName);
            properties.load(configReader);
            configReader.close();
        } catch (FileNotFoundException ignored) {
            // If the config does not exist, generate the default one.
            Logger.info("Generating the config file at: " + fileName);
            save();
            return; // Return here, so we don't try to read the config file
        } catch (IOException e) {
            // If we fail to read the config file, log the error and return
            Logger.info("Failed to read the config file: " + fileName);
            e.printStackTrace();
        }

        // Read the config values from the file:
        lootKey = properties.getProperty(Constants.CONFIG_LOOT_KEY);

    }

    private static int parseIntOrDefault(String s, int defaultValue) {
        // Try to parse the string as an integer, if it fails, return the default value
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void save() {
        // try to save the config file with the current values from memory
        try {
            File config = new File(fileName);
            boolean existed = config.exists();
            File parentDir = config.getParentFile();
            if (!parentDir.exists())
                parentDir.mkdirs();

            FileWriter configWriter = new FileWriter(config);

            // Write the values to the file
            writeString(configWriter, Constants.CONFIG_LOOT_KEY, lootKey);

            configWriter.close();

            if (!existed)
                Logger.info("Created the config file.");
        } catch (IOException e) {
            // If we fail to save the config file, log the error
            Logger.info("Failed to write the config file: " + fileName);
            e.printStackTrace();
        }
    }

    private static void writeString(FileWriter configWriter, String name, String value) throws IOException {
        configWriter.write(name + '=' + value + '\n');
    }

    private static void writeBoolean(FileWriter configWriter, String name, boolean value) throws IOException {
        writeString(configWriter, name, value ? "1" : "0");
    }

    static {
        // Default values for the config settings
        defaultValues.setProperty(Constants.CONFIG_LOOT_KEY, "C");
    }

    public String getLootKey() {
        return lootKey;
    }
}


