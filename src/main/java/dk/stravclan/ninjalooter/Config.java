package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Properties defaultValues = new Properties();
    private static final Logger Logger = LogUtils.getLogger();
    private String fileName;

    public boolean rmbTweak = true;
    public boolean lmbTweakWithItem = true;
    public boolean lmbTweakWithoutItem = true;
    public boolean wheelTweak = true;
    public static boolean debug = false;

    Config(String fileName) {
        this.fileName = fileName;
    }

    public void read() {
        Properties properties = new Properties(defaultValues);

        try {
            FileReader configReader = new FileReader(fileName);
            properties.load(configReader);
            configReader.close();
        } catch (FileNotFoundException ignored) {
            // If the config does not exist, generate the default one.
            Logger.info("Generating the config file at: " + fileName);
            save();
            return;
        } catch (IOException e) {
            Logger.info("Failed to read the config file: " + fileName);
            e.printStackTrace();
        }

        // Read the config values from the file:
//            rmbTweak = parseIntOrDefault(properties.getProperty(Constants.CONFIG_RMB_TWEAK), 1) != 0;
//            lmbTweakWithItem = parseIntOrDefault(properties.getProperty(Constants.CONFIG_LMB_TWEAK_WITH_ITEM), 1) != 0;
//            lmbTweakWithoutItem = parseIntOrDefault(properties.getProperty(Constants.CONFIG_LMB_TWEAK_WITHOUT_ITEM), 1)
//                    != 0;
//            wheelTweak = parseIntOrDefault(properties.getProperty(Constants.CONFIG_WHEEL_TWEAK), 1) != 0;
//            wheelSearchOrder
//                    = WheelSearchOrder.fromId(parseIntOrDefault(properties.getProperty(Constants.CONFIG_WHEEL_SEARCH_ORDER),
//                    1));
//            wheelScrollDirection
//                    =
//                    WheelScrollDirection.fromId(parseIntOrDefault(properties.getProperty(Constants.CONFIG_WHEEL_SCROLL_DIRECTION),
//                            0));
//            scrollItemScaling = ScrollItemScaling.fromId(parseIntOrDefault(properties.getProperty(Constants.CONFIG_SCROLL_ITEM_SCALING), 0));

        // debug = parseIntOrDefault(properties.getProperty(Constants.CONFIG_DEBUG), 0) != 0;
    }

    private static int parseIntOrDefault(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void save() {
        try {
            File config = new File(fileName);
            boolean existed = config.exists();
            File parentDir = config.getParentFile();
            if (!parentDir.exists())
                parentDir.mkdirs();

            FileWriter configWriter = new FileWriter(config);

            // Write the values to the file

//                writeBoolean(configWriter, Constants.CONFIG_RMB_TWEAK, rmbTweak);
//                writeBoolean(configWriter, Constants.CONFIG_LMB_TWEAK_WITH_ITEM, lmbTweakWithItem);
//                writeBoolean(configWriter, Constants.CONFIG_LMB_TWEAK_WITHOUT_ITEM, lmbTweakWithoutItem);
//                writeBoolean(configWriter, Constants.CONFIG_WHEEL_TWEAK, wheelTweak);
//                writeString(configWriter, Constants.CONFIG_WHEEL_SEARCH_ORDER, String.valueOf(wheelSearchOrder.ordinal()));
//                writeString(configWriter,
//                        Constants.CONFIG_WHEEL_SCROLL_DIRECTION,
//                        String.valueOf(wheelScrollDirection.ordinal()));
//                writeString(configWriter, Constants.CONFIG_SCROLL_ITEM_SCALING, String.valueOf(scrollItemScaling.ordinal()));
//                writeBoolean(configWriter, Constants.CONFIG_DEBUG, debug);

            configWriter.close();

            if (!existed)
                Logger.info("Created the config file.");
        } catch (IOException e) {
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
//            defaultValues.setProperty(Constants.CONFIG_RMB_TWEAK, "1");
//            defaultValues.setProperty(Constants.CONFIG_LMB_TWEAK_WITH_ITEM, "1");
//            defaultValues.setProperty(Constants.CONFIG_LMB_TWEAK_WITHOUT_ITEM, "1");
//            defaultValues.setProperty(Constants.CONFIG_WHEEL_TWEAK, "1");
//            defaultValues.setProperty(Constants.CONFIG_WHEEL_SEARCH_ORDER, "1");
//            defaultValues.setProperty(Constants.CONFIG_WHEEL_SCROLL_DIRECTION, "0");
//            defaultValues.setProperty(Constants.CONFIG_SCROLL_ITEM_SCALING, "0");
//            defaultValues.setProperty(Constants.CONFIG_DEBUG, "0");
    }
}


